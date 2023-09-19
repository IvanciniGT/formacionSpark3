import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class ContarHashtags {

    public static void main(String[] args) {
        // Me creo una lista con unos cuantos tweets
        List<String> tweets = Arrays.asList(
                "En la playa con mis amigos #SummerLove#GoodVibes",
                "Mierda de verano... estudiando... #CacaDeVerano",
                "En la playa con mi familia #GoodVibes",
                "Sacando a mi perro #DogsLove");

        List<String> palabrasProhibidas = Arrays.asList(
                "mierda", "caca");



        // Abrir una sesión (conexión) con el maestro de un cluster de Apache Spark

        final SparkConf configuracion = new SparkConf().setAppName("CalcularPI") // Identifica mi app en el cluster.
                .setMaster("local[2]");    // Contra que cluster trabajo
        // Lo que ésto hace es levantar en mi máquina
        // un cluster de spark con 1 trabajador
        // que tiene acceso a 2 cores de mi máquina

        //.setMaster("spark://<NOMBRE|IP>:<PUERTO>");
        JavaSparkContext conexion= null;
        try {
            conexion = new JavaSparkContext(configuracion);


            // Quiero pasar esto a un Stream de Java
            //tweets.stream() // para cada tweet
            conexion.parallelize(tweets) // para cada tweet
                    .map(tweet -> tweet.replaceAll("#", " #"))   // Reemplazo el "#" por un " #"
                    .flatMap(tweet -> Arrays.asList(tweet.split("[ .,_+(){}!?¿'\"<>/@|&-]+")).iterator())   // Parto el tweet en palabras (usando expresiones regulares)
                    .filter(palabra -> palabra.startsWith("#"))   // Me quedo con las palabras que empiezan por "#"
                    .map(String::toLowerCase)   // Convierto a minúsculas
                    .map(hashtag -> hashtag.substring(1))   // Quitar los cuadraditos
                    .filter(hashtag -> palabrasProhibidas.stream().filter(palabraIndeseada -> hashtag.contains(palabraIndeseada)).count() == 0)   // Me quedo con las que no contienen palabras de la lista de prohibidas
                    // .filter( hashtag -> palabrasProhibidas.stream().noneMatch(hashtag::contains) )
                    //.map( hashtag -> new Tuple2<>(hashtag, 1))  // Añadir un 1 a cada hashtag Si hago esto el objeto me devuelve un RDD<Tuple2<String, Integer>>
                    .mapToPair( hashtag -> new Tuple2<>(hashtag, 1))  // Añadir un 1 a cada hashtag Si hago esto el objeto me devuelve un PairRDD<String, Integer> que si tiene las funciones que necesito
                    .reduceByKey(Integer::sum)  // Sumar los 1s de cada hashtag
                    .mapToPair( tupla ->  new Tuple2<>(tupla._2, tupla._1))  // Intercambiar el orden de la tupla
                    .sortByKey(false)  // Ordenar por el número de ocurrencias descendente
                    .take(2) // Es mi función de reducción
                    .forEach(hashtag -> System.out.println(hashtag));                                                          // Al final sacar el resultado por pantalla
        } finally {
            // Cierro conexión con el cluster de Spark
            conexion.close();
        }
    }
    // En java, la función que ponemos dentro de un flatMap debe devolver un Stream
    // En Spark, la función que ponemos dentro de un flatMap debe devolver un objeto iterable
}
