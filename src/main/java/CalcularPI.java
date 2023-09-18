import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CalcularPI  {

    public static void main(String[] args) {
        int totalDeDardos = 1000 * 1000 * 1000;
        System.out.println("PI es aproximadamente " + new CalcularPI().estimarPI(totalDeDardos));

        int numeroDeTrabajadores = 4;
        int totalDeDardosPorTrabajador = totalDeDardos / numeroDeTrabajadores;


        List<Integer> dardosPorTrabajador = new ArrayList<>();
        for (int i = 0; i < numeroDeTrabajadores; i++)
            dardosPorTrabajador.add(totalDeDardosPorTrabajador);

        // Llegados a este punto tengo una lista con cuántos elementos? 4
        // Qué tengo en cada posición? [ 2500000, 2500000, 2500000, 2500000 ]

        // Qué quiero hacer con cada uno de esos datos? estimo PI... y PI lo estimo a través de la función estimarPI
        // Que devuelve una estimación de PI (double)

        // numeroDeDatos -> estimarPI(numeroDeDatos)

        double ESTIMACION_PI = dardosPorTrabajador.parallelStream()             // Para cada conjuntoDeDardos... en paralelo
                           .map(CalcularPI::estimarPI)                          // Estima PI
                           .reduce(  (estPi1, estPi2) -> estPi1 + estPi2   )    // Sumo las estimaciones. Igual que poner Double::sum
                           .get()                                               // Saco lo que hay dentro del optional
                           / numeroDeTrabajadores ;                             // Divido entre el número de trabajadores

        System.out.println("PI es aproximadamente " + ESTIMACION_PI);

        // Vamos a hacer otra implementación, en este caso sin usar la función estimarPI
            // IntStream.range
            // flatMap
            // mapToDouble
        // Partimos de los mismos datos que arriba:
        // int numeroDeTrabajadores = 4;
        // int totalDeDardosPorTrabajador = totalDeDardos / numeroDeTrabajadores;

        double OTRA_ESTIMACION_DE_PI = IntStream.range(0, numeroDeTrabajadores)    // IntStream     Tengo un alista con todos los trabajadores                                     [0,1,2,3]
                .map(trabajador -> totalDeDardosPorTrabajador)                     // IntStream     Tengo una lista con el número de dardos que debe tirar cada trabajador         [2500000, 2500000, 2500000, 2500000]
                .parallel()                                                        // IntStream
                .flatMap(numeroDeDardos -> IntStream.range(0, numeroDeDardos))      // IntStream     Tengo una "lista" con todos los dardos. [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14...,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14...,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14...,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14...]
                .mapToDouble(dardo -> Math.sqrt(Math.pow(Math.random(), 2) + Math.pow(Math.random(), 2))) // DoubleStream      De cada dardo calculo su distancia al centro de la diana [0.98, 1.23, 0.1....]
                .filter(distancia -> distancia <= 1)                                // DoubleStream      Me quedo solo con los dardos que han caido dentro de la diana
                .count() * 4. / totalDeDardos;                                     // Double            Calculo la estimación de PI

        System.out.println("PI es aproximadamente " + OTRA_ESTIMACION_DE_PI);
        // Las funciones lambda son SERIALIZABLES PER SE... y eso me interesa montñoin con Spark.
    }

    public static double estimarPI( int numeroTotalDeDardos) {
        int numeroDardosDentro = 0;

        for (int dardo = 0; dardo < numeroTotalDeDardos; dardo++) {
            double x = Math.random();
            double y = Math.random();

            double distanciaAlCentro = Math.sqrt(x * x + y * y);
            if (distanciaAlCentro <= 1) numeroDardosDentro++;
        }

        return 4. * numeroDardosDentro / numeroTotalDeDardos;
    }

    // Si tengo 4 cores cómo podría paralelizarlo:
    // 1. Divido el trabajo en 4 partes.
    // 2. Cada hilo hace su trabajo y me devuelve un resultado. Su estimación de PI
    // 3. Hago la media de las 4 estimaciones de PI
}
