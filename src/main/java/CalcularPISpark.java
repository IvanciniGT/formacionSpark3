import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CalcularPISpark {

    public static void main(String[] args) throws Exception {

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
            int totalDeDardos = 1 * 1000 * 1000;
            int numeroDeTrabajadores = 4;
            int totalDeDardosPorTrabajador = totalDeDardos / numeroDeTrabajadores;

            List<Integer> dardosPorTrabajador = new ArrayList<>();
            for (int i = 0; i < numeroDeTrabajadores; i++)
                dardosPorTrabajador.add(totalDeDardosPorTrabajador);

            // Compatibilizar los datos con Spark
            // En Spark no trabajamos con Streams... sino con RDDs

            // dardosPorTrabajador.parallelStream() -> Devuelve un RDD
            double ESTIMACION_PI = conexion.parallelize(dardosPorTrabajador)     // Para cada conjuntoDeDardos... en paralelo
                    .repartition(20)                            // Particiones que se trabajan de mis datos (Parto el trabajo en 20 partes...
                                                                            // Que voy repartiendo entre los 2 ejecutores que tengo
                    .map(CalcularPISpark::estimarPI)                        // Estima PI
                    .reduce((estPi1, estPi2) -> estPi1 + estPi2)            // Sumo las estimaciones. Igual que poner Double::sum
                    / numeroDeTrabajadores;                                 // Divido entre el número de trabajadores

            System.out.println("PI es aproximadamente " + ESTIMACION_PI);
        } finally {
            // Cierro conexión con el cluster de Spark
            conexion.close();
        }
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

}
