import java.util.function.*; // JAVA 1.8
// Consumer<T>      // Función que recibe un parámetro de tipo T y no devuelve nada
// Supplier<T>      // Función que no recibe nada y devuelve un parámetro de tipo T
// Function<T,R>    // Función que recibe un parámetro de tipo T y devuelve un parámetro de tipo R
// Predicate<T>     // Función que recibe un parámetro de tipo T y devuelve un booleano (is, has, ...)

// La función println que tenemos dentro de System.out es de tipo Consumer<Objeto>
// La función doble que tenemos definida en esta clase es de tipo Function<Integer,Integer>
public class ProgramacionFuncional {

    public static void main(String[] args){
        String texto = "Hola";
        // Sacarlo por pantalla
        System.out.println(texto);

        int numero = 4;
        // Sacarlo por pantalla el doble usando mi función
        System.out.println(doble(numero));                       // Invoco a la función doble que está definida en esta clase
        System.out.println(ProgramacionFuncional.doble(numero)); // Invoco a la función doble que está definida en esta clase

        Function<Integer,Integer> miFuncion = ProgramacionFuncional::doble; // JAVA 1.8   Operador :: para referenciar una función

        int numero2 = miFuncion.apply(5); // Ejecutando la función. Esto es equivalente a haber escrito double(5)
        System.out.println(numero2);

        imprimirResultadoDeOperacion(ProgramacionFuncional::doble, 6);
        imprimirResultadoDeOperacion(miFuncion, 7);
        // Qué consigo con esto... delegar la ejecución de una función en otra función
        // Y... esto pa' que vale?
        // Esto sirve cuando quiero definir una función que tiene que hacer algo... que no sabe lo que es... ya lo se lo diré

        // En java 1.8 se añade otro operador, además del ::, el operador ->
        // Que nos permite definir una expresión lambda
        // Qué es una expresión lambda?
            // Es un trozo de código que devuelve una función anónima creada dentro de un statement
        // Qué es una expresión?    Un trozo de código que devuelve un valor
        int numero3 = 4;        // Esto es un Statement: Enunciado
        int numero4 = 5 + 7;    // Esto es otro Statement
                      /////   <- Eso de ahí es una expresión

        Function<Integer,Integer> otraFuncion = (Integer a) -> {
                                                                    return a*3;
                                                                };       // El tipo de dato de vuelta se INFIERE
                                                //////////////////////////////// Lo que hace es devolver una función anónima
        Function<Integer,Integer> otraFuncion2 = (Integer a) -> { return a*3; };
        Function<Integer,Integer> otraFuncion3 = (a) -> { return a*3; }; // Puedo inferir también si quiero el tipo de dato de entrada
        Function<Integer,Integer> otraFuncion4 = a -> { return a*3; }; // Puedo inferir también si quiero el tipo de dato de entrada
        Function<Integer,Integer> otraFuncion5 = a -> a*3; // Puedo inferir también si quiero el tipo de dato de entrada
        Function<Integer,Integer> otraFuncion6 = ProgramacionFuncional::triple;

        // Me temo, que el hecho de que las expresiones LAMBDA sean más cómoda en algunos escenarios...
        // no va a ser su única ventaja. EN SPARK ME INTERESA USAR TANTO COMO PUEDA EXPRESIONES LAMBDAS
        // Ya que, la Máquina virtual de Java las crea de forma que son SERIALIZABLES son yo hacer nada
        // Ademças, el propio código de la función se manda por red.
        imprimirResultadoDeOperacion( n -> n/2 , 10);
    }

    public static void imprimirResultadoDeOperacion(Function<Integer, Integer> operacion, int numero){
        System.out.println(operacion.apply(numero));
    }

    public static int doble(int a){
        return a*2;
    }
    public static int triple(int a){
        return a*3;
    }

}
