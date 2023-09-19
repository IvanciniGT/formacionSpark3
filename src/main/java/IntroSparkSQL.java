import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.col;

public class IntroSparkSQL {

    public static void main(String[] args) throws AnalysisException {
        // Abrir una conexión con Spark
        SparkSession conexion = SparkSession.builder()
                                            .appName("introSQL")
                                            .master("local")
                                            .getOrCreate();
        // Código para procesar datos
        // En SparkSQL no trabajamos con RDDs ( lo que era el equivalente a los Streams de JAVA)
        // En su lugar tenemos un nuevo tipo de datos: Dataset<Row>... y nos representan lo que vendría a ser una Tabla de una BBDD
        // El Dataset tiene filas... y las filas tiene columnas.
        Dataset<Row> datos = conexion.read().json("src/main/resources/personas.json");
        datos.show();           // Nos muestra el conjunto de datos.
        datos.printSchema();    // Nos muestra el esquema de los datos

        datos.select("nombre", "apellido") // Me devuelve otro Dataset<Row> con las columnas que le pido
             .show();
        // En todas las funciones que vamos a ver yuo puedo:
        // - Pasarle los nombres "string" de las columnas... como en el ejemplo anterior
        // - Pasarle las columnas como objetos... y eso me ofrece nuevas oportunidades: como en el ejemplo siguiente
        datos.select(col("nombre"), col("apellido"))
             .show();

        datos.select(col("nombre"), col("edad").plus(100)).show();
        datos.select(col("nombre"), col("edad").gt(18)).as("MayorDeEdad").show();

        datos.select("nombre").groupBy("nombre").count().show();
        datos.select("nombre","edad")
                .groupBy("nombre")
                .sum("edad")
                .orderBy(col("sum(edad)").desc())
                .show();

        datos.createTempView("personas");
        Dataset<Row> datosAgregados = conexion.sql("SELECT nombre, sum(edad) FROM personas GROUP BY nombre ORDER BY sum(edad) DESC");
        // La librería SparkSQL transforma ese SQL en un conjunto de operaciones Map Reduce de Apache Spark Core
        datosAgregados.show();

        // La librería SparkSQL y la librería Spark Core puedo usarlas conjuntamente
        // Hay cosas, que es muy cómodo hacer en SparkCore y otras que es muy cómodo hacer en SparkSQL
        //Dataset<Row> datos
        JavaRDD<Row> datosComoRDD = datos.toJavaRDD();
        JavaRDD<Persona> personas = datosComoRDD.map(Persona::crearPersona);
        JavaRDD<Persona> personasValidas = personas.filter(Persona::validarEmail);
        Dataset<Row> personasValidasComoDataset = conexion.createDataFrame(personasValidas, Persona.class);
        personasValidasComoDataset.show();


        conexion.createDataFrame(
            datos.toJavaRDD()
                 .map(Persona::crearPersona)
                 .filter(Persona::validarEmail)
            ,Persona.class)
                .select("nombre","email")
                .orderBy("email")
                //        .write().csv("src/main/resources/personas.csv");
                        .write().parquet("src/main/resources/personas.parquet");

        // Cerramos conexión con spark
        conexion.close();
    }

}
