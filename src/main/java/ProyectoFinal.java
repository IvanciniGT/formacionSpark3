import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;

import java.util.HashMap;
import java.util.Map;

import static org.apache.spark.sql.functions.col;

public class ProyectoFinal {

    public static void main(String[] args) throws Exception{
        // Lo primero abrimos conexión con el cluster de Spark
        System.out.println("Iniciamos el programa");
        System.out.println("Creamos la conexión con el cluster de Spark");
        SparkSession conexion = SparkSession.builder()                  // Comenzamos a configurar la conexión
                                            .appName("ProyectoFinal")   // Doy el nombre de mi proyecto en el cluster
                                            .master("local")            // Contra que cluster trabajo
                                            .getOrCreate();             // He acabado de configurar. Dame la conexión
        System.out.println("Conexión con el cluster de Spark creada");

        // Leo el fichero de Personas
        System.out.println("Configuro cómo se debe leer el fichero de Personas");
        Map<String,String> opciones = new HashMap<>();
        opciones.put("delimiter", ",");
        opciones.put("header", "true");

        System.out.println("Leo el fichero de Personas, con DNI y CP");
        Dataset<Row> personasSinValidar = conexion.read()
                                                  //.option("delimiter", ",")
                                                  //.option("header", "true")
                                                  .options(opciones)
                                                  .csv("src/main/resources/personasDNI.csv");

        System.out.println("Fichero de Personas leído");
        personasSinValidar.show();

        System.out.println("Miramos los tipos de datos que se han asignado");
        personasSinValidar.printSchema();
             // NOTA: El campo CP en la tabla de personas es un texto

        // Validar los DNIs
        // Mediante un Dataset<Row>
        System.out.println("Validamos los DNIs");
        conexion.udf().register("validarDNI", ProyectoFinal::validarDNI, DataTypes.BooleanType); // Registro mi función en SparkSQL
        Dataset<Row> personasValidadas = personasSinValidar.withColumn("DNIValido",           // Creo una nueva columna llama DNIValido
                                              functions.callUDF("validarDNI",                 // La compongo con el resultado de ejecutar la función validarDNI
                                                                col("dni")));                 // sobre la columna dni
        personasValidadas.show();
        System.out.println("DNIs validados.");
        // Mediante un RDD
        // JavaRDD<Row> personasConDNIOK = personasSinValidar.toJavaRDD()                                // Convertir el Dataset a RDD
        //                                                  .filter( fila -> validarDNI(                  // De cada fila, valida el DNI
        //                                                          fila.get(fila.fieldIndex("dni")))    // Que está en la columna "dni"
        //                                                  );
        // personasConDNIOK.foreach( fila -> System.out.println(fila) );

        // Guardar las personas que no tienen un DNI válido en un fichero
        Dataset<Row> personasConDNI_NOK = personasValidadas.filter(col("DNIValido").equalTo(false));
        System.out.println("Guardamos las personas con DNI no válido en un fichero");
        personasConDNI_NOK.limit(50).show(); // Muestro 50 datos... para ver si esto va bien
        personasConDNI_NOK.write().mode("overwrite").parquet("src/main/resources/personasConDNI_NOK.parquet");


        Dataset<Row> personasConDNI_OK  = personasValidadas.filter(col("DNIValido").equalTo(true));

        ////// SEGUNDA PARTE DE NUESTRO PROYECTO //////////////////////////////////////////////////////////////////////

        System.out.println("Leemos el fichero de CPs");
        Dataset<Row> cps = conexion.read().json("src/main/resources/cp.json");
        System.out.println("Fichero de CPs leído");
        cps.show();
        cps.printSchema();

        // Unir los datos de personas con los datos de CPs
        System.out.println("Unimos los datos de personas con los datos de CPs");
        Dataset<Row> personasConDNI_OKyCPs = personasConDNI_OK.join(cps, "cp"); //personasConDNI_OK.col("cp").equalTo(cps.col("cp")));
        personasConDNI_OKyCPs.show();
        personasConDNI_OKyCPs.write().mode("overwrite").parquet("src/main/resources/personasConDNI_OKyCPs.parquet");

        // Extraer las personas cuyo CP no está registrado en la tabla cps
        System.out.println("Extraemos las personas cuyo CP no está registrado en la tabla cps");
        Dataset<Row> personasConDNI_OKyCP_NOK = personasConDNI_OK.except(personasConDNI_OKyCPs.select("nombre","apellidos","cp","dni","DNIValido"));
        personasConDNI_OKyCP_NOK.show();
        personasConDNI_OKyCPs.write().mode("overwrite").parquet("src/main/resources/personasConDNI_OKyCP_NOK.parquet");
        // En los ficheros, en el ejemplo, estamos usando rutas locales
        // Cuando trabajamos con un cluster de Spark, las rutas deben ser accesibles desde todos los nodos del cluster
        // Lo que trabajamos son con carpetas en HDFS, S3, etc...

        //personasConDNI_OKyCPs.drop("provincia","poblacion").show();
        //Dataset<Row> personasConDNI_OKyCP_NOK = personasConDNI_OK.except(personasConDNI_OKyCPs.drop("provincia","poblacion"));

        // Cerramos conexión con el cluster de Spark
        conexion.close();
    }

    private static Boolean validarDNI(Object dniAValidarComoObjeto) {
        String dniAValidar = dniAValidarComoObjeto.toString();
        if (!dniAValidar.matches("^[0-9]{1,8}[A-Z]$")) return false;
        // Separamos la parte numérica del DNI de la lectra de Control
        String parteNumerica = dniAValidar.substring(0, dniAValidar.length() - 1);
        String letraDeControl = dniAValidar.substring(dniAValidar.length() - 1);
        int resto = Integer.parseInt(parteNumerica) % 23; // Me quedo con el resto al dividir entre 23, que estará entre: 0-22
        String letrasPosibles = "TRWAGMYFPDXBNJZSQVHLCKE";
        String letraCalculada = letrasPosibles.substring(resto, resto + 1); // Mirar que letra correspondería según el resto
        // Y la comparo con la que me han dado
        return letraCalculada.equals(letraDeControl);
    }
}
