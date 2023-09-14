import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class TrabajandoConStreams {

    public static void main(String[] args){
        List<Integer> numeros = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10); // Java 9
        //List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Bucles en JAVA
        // Antes de Java 1.5. habríamos escrito:
        for(int i=0; i<numeros.size(); i++){
            System.out.println(numeros.get(i));
        }
        // Después de Java 1.5 y antes de Java 1.8. En Java 1.5 aparece el concepto de Iterable
        for(Integer numero: numeros){
            System.out.println(numero);
        }
        // Después de JAVA 1.8 y la programación funcional
        //numeros.forEach( numero -> System.out.println(numero) );
        numeros.forEach(System.out::println);

        // Cualuiqer colección de Java la puedo pasar a un stream:
        Stream<Integer> streamDeNumeros = numeros.stream();

        // Que es un Stream? Es una colección de datos que se procesan
        // mediante programación funcional, habitualmente según un modelo MapReduce

        Stream<Integer> salida = streamDeNumeros.map( TrabajandoConStreams::triple );
        salida.forEach( System.out::println );

        numeros.parallelStream()                        // Para cada numero
        //spark.parallelize(numero)
                .map( n -> n*3 )                        // Java ! Apunta multiplicar por 3 cada numero
                .filter( n -> n % 2 == 0 )              // Java ! Apunta que me quiero quedar solo con los pares
                .sequential()                           // Java ! Apunta que quiero que vuelvas a ser un stream secuencial
                .sorted( )                              // Java ! Apunta que quiero que ordenes los datos
                .forEach( System.out::println );        // ahora! a por ello ! Multiplica, filtra, ordena y saca por pantalla

        // Esto también puedo hacerlo con programación imperativa
        // Quiero coger los números, multiplicarlos por 3, quitar los impares y ordenarlos... luego los saco por pantalla
        List<Integer> otraLista = new ArrayList<>();
        for(Integer numero: numeros){
            int numeroMultiplicadoPor3 = numero*3;
            if( numeroMultiplicadoPor3 % 2 == 0 ){
                otraLista.add(numeroMultiplicadoPor3);
            }
        }
        otraLista.sort( (a,b) -> a.compareTo(b) );
        for(Integer numero: otraLista){
            System.out.println(numero);
        }

        // Tengo cuántos números? 10
        // Y si tengo 1000000000? cúanto va a tardar esto? Mucho
        // Y el proceso que tenemos hace uso de ... disco? NO
        // Y el proceso que tenemos hace uso de ... red? NO
        // Y el proceso que tenemos hace uso de ... memoria? SI
        // Y el proceso que tenemos hace uso de ... CPU? SI.. ésto son puros cálculos.. Me va a poner la cpu pa' freir huevos !!!"
        // O no? A cúanto vería la CPU si tengo 8 cores y ejecuto este trabajo? 12.5%
        // Y mi cpu no me permitiría freir ni un triste huevo
        // Por qué? Porque mi código está siendo ejecutado por un único hilo: Thread
        // Y un hilo solo usa un core
        // Si quiero sacarle partido a mi máquina... y acabar hoy... y de paso cenar (huevos)
        // Me toca abrir hilos... y eso es MUY COMPLEJO EN JAVA

        System.out.println(numeros);
        Optional<Integer> total = numeros.stream()
                .reduce( (a, b) -> a + b );

        System.out.println(total);

        // De momento hemos visto ya algunas funciones tipo map: map, filter, sorted
        // Pero hay más..
        // Hay una que usamos bastante: flatMap

        /*
        Stream   -> .map(doble) -> Stream
         1                           2
         2                           4
         3                           6

        factorizar números en matemáticas:   Si factorizo el 6 =    [2, 3]
        factorizar números en matemáticas:   Si factorizo el 8 =    [2, 2, 2]
        factorizar números en matemáticas:   Si factorizo el 12 =   [2, 2, 3]

        Stream    -> .map( factorizar ) -> Stream
         6                                   [2, 3]
         8                                   [2, 2, 2]
         12                                  [2, 2, 3]

                                                PASO PREVIO
        Stream    -> .flatMap( factorizar ) -> Stream       -> Aplanar ese Stream (descomponer cada lista en sus elementos)
         6                                   [2, 3]                     2
         8                                   [2, 2, 2]                  3
         12                                  [2, 2, 3]                  2
                                                                        2
                                                                        2
                                                                        2
                                                                        2
                                                                        3

         Un flatMap primero hace un MAP y luego un FLATTEN
            La función de transformación que suministre en un flatMap debe devolver un Stream



        */

        // Imaginad que quiero un Stream de números enteros
        Stream<Integer> listadoDeNumerosEnteros;
        // Inventar/Crear Clases para representar los tipos simples... de forma que los pueda meter en Colecciones o suministrarlos como genéricos
        // int -> Integer
        // double -> Double
        // boolean -> Boolean
        // char -> Character

        // Al menos se tomaron la dignidad de convertir automaticamente los tipos primitivos en sus clases equivalentes
        // mediante un procedimiento que se llama autoboxing
        List<Integer> listaDeNumerosEnteros = new ArrayList<>();
        int numero= 1;
        listaDeNumerosEnteros.add(numero);  // Se hace un autoboxing

        // Esto mete una sobrecarga brutal en la memoria
        // Por eso motivo, cuando queremos trabajar con colecciones grandes de datos,
        // el trabajar con los Tipos Primitivos nos es más interesante, que
        // trabajar con sus clases equivalentes

        // El los Streams pasa lo mismo, que con las listas normales... y el autoboxing

        // En Java 1.8 se crean una seria de Streams para trabajar con tipos primitivos,
        // que son mucho más eficientes que los Streams normales equivalentes

        // Stream<Integer> -> IntStream
        // Stream<Double>  -> DoubleStream
        // Stream<Boolean> -> BooleanStream

        // Podemos pasar de un Stream<Integer> a un IntStream, mediante un proceso que se llama unboxing
        // Podemos pasar de un IntStream a un Stream<Integer>, mediante un proceso que se llama boxing
        // tenemos algunas funciones que nos permiten hacerlo:
        // .mapToInt()      -> Convierte un Stream<Integer> en un IntStream
        // .mapToDouble()   -> Convierte un Stream<Integer> o un IntStream en un DoubleStream
        // Y lo contrario
        // .boxed()         -> Convierte un IntStream en un Stream<Integer>

        // Hay una función chula que tenemos en los IntStream
        // .range(0, 10) -> Genera un IntStream con los números del 0 al 9

    }

    public static int triple(int a){
        return a*3;
    }
}
