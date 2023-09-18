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

        // Quiero pasar esto a un Stream de Java
        tweets.stream() // para cada tweet
                // Aqui hay que rellenar con código
                // Reemplazo el "#" por un " #"
                .map(      tweet -> tweet.replaceAll("#"," #")                      )
                // Parto el tweet en palabras (usando expresiones regulares)
                .flatMap(  tweet -> Arrays.stream( tweet.split("[ .,_+(){}!?¿'\"<>/@|&-]+") )   )
                    // En java, la función que ponemos dentro de un flatMap debe devolver un Stream
                    // En Spark, la función que ponemos dentro de un flatMap debe devolver un objeto iterable
                // Me quedo con las palabras que empiezan por "#"
                .filter(   palabra -> palabra.startsWith("#")                                         )
                // Convierto a minúsculas
                .map(     String::toLowerCase                                                        )
                // Quitar los cuadraditos
                .map(     hashtag -> hashtag.substring(1)                                  )
                // Me quedo con las que no contienen palabras de la lista de prohibidas
                .filter( hashtag -> palabrasProhibidas.stream().filter( palabraIndeseada -> hashtag.contains(palabraIndeseada) ).count() == 0 )
                // .filter( hashtag -> palabrasProhibidas.stream().noneMatch(hashtag::contains) )
                .forEach(System.out::println);

        // Al final sacar el resultado por pantalla
    }
}
