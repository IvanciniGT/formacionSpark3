import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.col;

public class IntroSparkSQL {

    public static void main(String[] args) {
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

        // Cerramos conexión con spark
        conexion.close();
    }

}
