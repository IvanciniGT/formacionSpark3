
# Otras novedades de Java 1.8

Librería que me permita buscar palabras en un diccionario para saber si existen... y caso que existan que me de sus significados.

```java

public class Diccionario {

    public boolean existe(String palabra) { 
        ... 
    }

    public List<String> getSignificados(String palabra) {
        ... 
    }
    // SonarQube: ESTO ES UNA MIERDA GIGANTE !!!!!
    // Es una MIERDA porque no sois capaces de decirme que devuelve la función... su comportamiento externo.

    // Qué devuelve?
    //  - Una lista vacía       \   Porque no son explícitas
    //  - null                  /
    //                                  Al menos me muestra un comportamiento mucho más diferenciado para tratar los 2 posibles casos 
    //                                  de esta función
    //  - Lanzar una Exception  -   Si es explícita ( throws NoSuchWordException )
    //                                  El problema es que NUNCA JAMAS EN LA VIDA debería usar una excepción para controlar lógica de negocio
    //                                  Una Exception es para otras cosas... ya que además es MUY CARA DE GENERAR
    //                                  Lo primero que es necesario hacer para lanzar una Exception es un volcado del Stack de Hilos

    // llamo a la función para el diccionario de Español... con la palabra "ARCHILOCOCO"

    // desde JAVA 1.8, todas esas están consideradas MUY MUY MUY MALAS PRACTICAS... el Sonar os las escupe a la cara !
    // En Java 1.8 aparece la clase Optional
    public Optional<List<String>> getSignificados(String palabra) {
        ... 
    }

    // Un optional es una caja... que puede tener dentro algo o no
    // A un optional le puedo preguntar:
    // .isEmpty()
    // .isPresent()
    // .get()
    // .getOrElse(ESTO OTRO)
    // .getOrNull()
}

```

Cuando queramos llevar algo a Spark... TODOS LOS PROGRAMAS SPARK SON IGUALES:

Parte 1: Abrir una conexión con el cluster de Spark
Parte 2: Cargar los datos
Parte 3: Configurar el procesamiento de los datos (tipo MAP)
Parte 4: Ejecutar el procesamiento de los datos (función tipo REDUCE)
    Aquí pasa magia:
        Mis datos se pasan al cluster de Spark (si es que no están allí ya)
        El código de mis transformaciones (map) tiene que estar en el cluster de Spark
        Al final, el cluster de Spark, contesta con los resultados de la transformación
Parte 5: Hago lo que quiera con los resultados
Parte 6: Cerrar la conexión con el cluster de Spark

                                    |                           CLUSTER SPARK                          |  
MI MAQUINA LOCAL    ------------>   |   Maestro del cluster (Driver)    |   Worker 1       | worker2   |
  java -jar mi-programa.jar         |     java arrancado                |    java          |   java    |
  submit-spark-app mi-programa.jar         mi-programa.jar             mi-programa.jar   mi-programa.jar
                                                ^^^^ Yo lo pongo aquí         ^^^^ El maestro del cluster lo pone aquí
                                                     Eso va a ser muy sencillo. 
                                                     Spark me da un comando para arrancar mi programa en local ... y de paso enviar mi jar al maestro
 Si tengo aquí un objeto JAVA
 y sobre él hago operaciones
 Ese objeto lo tengo que mandar al maestro 
 y viaja por la red -> SERIALIZABLE