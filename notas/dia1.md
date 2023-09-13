# Bigdata

Conjunto de estrategias para trabajar con datos cuando las técnicas/herramientas tradicionales que hemos venido usando para ello NO ME SON SUFICIENTES.

Por qué me puede pasar esto?
- Gran volumen de datos (Cantidades aberrantes de datos)
- Volumen de generación de datos muy grande, que necesitan ser procesados en una ventana de tiempo muy pequeña
- Datos muy complejos de tratar (documentos pdf, video, ...)

## Ejemplo 1

Quiero llevar un listado de cosas:
100 cosas   -> Notas, Excel
50.000 cosas -> MsAccess
500.000 cosas -> MySQL/MariaDB
5.000.000 cosas -> MS SQL Server
100.000.000 cosas -> Oracle DB
1.000.000.000 cosas -> ORACLE MUERE !
Y yo digo y ahora qué?

## Ejemplo 2

1 segundo yo hago 2 movimiento en mi pantalla
Cada movimiento implica mandar 3 mensajes hacia los teléfonos de los participantes
Pero en un seg hago 2 mov... 6 mensajes
Pero jugamos 4 -> 24 mensajes por segundo

50.000 partidas x 24 = 1.200.000 mensajes al segundo

Con los datos podemos hacer muchas cosas:
- quiero guardar el dato
- quiero analizar el dato
- quiero transmitir el dato

## Ejemplo 3

Tengo un primo de un amigo de un compañero del trabajo que descargó una peli de internet. Fichero de 5Gbs
- Tengo un pincho USB de 16 Gbs ... pelao... lo acabo de comprar... ta más vacío que el desierto
  Le entra la peli ahí? Depende del sistema de archivos con que esté formateado: FAT-16 o FAT-32 ni de coña...
  solo se admiten archivos de un máximo de 2 Gbs
- NTFS, ext4... que también tienen su límite
- Tengo un archivo de 1 Pb = 1000 Tb

  1 Kb = 1000 b
  1 Kib = 1024 b
  1 Pib = 1024 Tib

## Solución

En lugar de ir a un sistema / servidor / programa más grande... voy a MONTONES más pequeños.
Vamos a trabajar con un enjambre de máquinas, que todas ellas van a trabajar como si fueran 1 de cara a resolver el problema.

BigData tiene que ver con utilizar un cluster de máquinas COMMODITY HARDWARE (maquinas de mierda) x 200

Voy a tener muchas máquinas... pero necesito orquestar su trabajo. COMPLEJO !
Los que empezaron con problemas fueron los de GOOGLE -> Paper BigTable
En un momento dado, inspirándose en estos documentos desarrollan: Apache Hadoop

Apache Hadoop es el equivalente a un SO en el mundo BigData.

SO. Responsabilidades:
- Controlar los recursos HW de la máquina: CPUs / RAM
- Controlar los procesos que ejecuto en la computadora y cómo les asigno acceso a esos recursos
- Gestión del almacenamiento

CLUSTER DE MAQUINAS

    máquina 1* JEFE SUPREMO DEL CLUSTER
        hadoop + spark
    máquina 2
        hadoop + spark
    ....
    máquina N
        hadoop + spark

Hadoop nos ofrece 3 cositas:
- YARN -> Programa capaz de controlar todas esas maquinitas... y saber si están operativas.. si se han caído...
- HDFS: Hadoop File System
  Un sistema de archivos distribuido. Coge un archivo GRANDE y lo parte en trozos (60Mb)
  Y cada trozo lo guardo en en al menos 3 computadores diferentes
- Modelo de procesamiento MapReduce: Forma de procesar programas que manipulan datos de forma distribuida

Pero... me temo.. que al final.. para qué vale un Sistema Operativo? Windows
El hardware coge valor según le voy poniendo encima más SOFTWARE (software muy específico) que me ayuda a resolver problemas concretos.

Y empieza a salir mucho software que es capaz de trabajar sobre un cluster que tenga instalado HADOOP, aprovechando las virtudes de HADOOP:

BBDD BigData:
- Casandra
- Hive
  Librerías para procesar y manipular datos:
- Spark
- Storm
  Herramientas de mensajería:
- Kafka

# Spark

Apache Spark es un a librería de Apache para el tratamiento/procesamiento de información en BIGDATA.

Apache Spark lo que no ofrece es una re-implementación del modelo MAP REDUCE que ofrece de forma nativa HADOOP.
El modelo map reduce que lleva hadoop no es muy eficiente... Ya que se basa en escribir continuamente toda la información a HDD, se necesite o no.

Por el contrario Apache Spark trabaja sobre memoria RAM... lo que es mucho más eficiente.

Ésto es lo que llamamos SparkCore.
Pero Spark tiene a su vez distintas librerías dentro (componentes):
- SparkML   Machine learning
- SparkSQL*  Re-implementación del API Core de Spark para facilitar su uso a la población general de informáticos.
- Spark Streaming  Hacer tratamiento de datos en "tiempo real".. ya que spark core está pensado para tratamiento BATCH
  Esta librería desde hace poco tiempo esta DEPRECATED !
  Y su funcionalidad se la han llevado a SparkSQL

# Organización del curso:

- JAVA: A aprender un poquito de programación funcional:
    - java.util.functions
    - Operador ::
    - Operador ->
    - java.util.Stream        * MapReduce
- Spark Core
  v
- Spark SQL Parte I
- Spark Streaming
- Spark SQL Parte II


---
# Paradigma de programación

Una forma de usar un lenguaje.
Lenguaje de programación (JAVA) ... que no deja de ser un LENGUAJE = ESPAÑOL, CATALAN, INGLES, EUSKERA,...

Qué tiene un lenguaje que le confiere ese nombre: GRAMATICA
- Morfología        FORMA DE CREAR VOCABLOS Y SUS TIPOS
- Sintaxis          REGLAS DE USO DE VOCABLOS
- Semántica         SIGNIFICADO DE ESOS VOCABLOS

## Paradigmas que encontramos en el mundo de la programación:

- Imperativo                Cuando doy instrucciones (órdenes) que se ejecutan secuencialmente: IF, FOR, WHILE...
- Procedural                Cuando el lenguaje me permite crear/definir funciones con instrucciones REUTILIZABLES
  de forma que posteriormente pueda invocarlas
- Funcional                 Cuando el lenguaje me permite que una variable referencie una función.
  Y posteriormente ejecutar esa función desde la variable.

                                Para que sirve esto... o que impacto tiene esto?
                                Y desde el momento que pudo referenciar una función desde una variable
                                - puedo pasar a una función otra función como argumento
                                - puedo hacer que una función devuelva otra función

- Orientado a Objetos       Cuando el lenguaje me permite definir mis propios tipos de datos,
  son sus atributos y sus funciones

                                        Propiedades                     Funciones
                            int             valor                           + - 
                            String          secuencia de caracteres         .toUpperCase() .trim()
                            List            secuencia de objetos mutable    .size() .get(index)

                            Usuario         nombre, apellidos, email        .esMayorDeEdad()

Debajo de la venta hay una silla                        AFIRMATIVA
FELIPE, pon una silla debajo de la ventana!             Frase, Sentencia, Oración, Enunciado.  IMPERATIVA
FELIPE, debajo de la ventana quiero una silla           DESIDERATIVA

## Variables en JAVA

Uns referencia a un dato almacenado en memoria.

String texto = "HOLA";

- "HOLA"        -> Crea un objeto de tipo String con valor HOLA en la memoria RAM
- String texto  -> Crea una variable llamada "texto" que puede apuntar a objetos de tipo String
- =             -> Asigna la variable texto al valor hola

texto = "ADIOS";

- "ADIOS"       -> Crea un objeto de tipo String con valor ADIOS en la memoria RAM
  Dónde se crea? Donde ponía "HOLA". En otro sitio
- texto         -> Despega la variable "texto" de donde estaba
- =             -> Y asigna esa variable al valor adios
  "HOLA" se acaba de convertir en BASURA -> GARBAGE.... GARBAGE COLLECTOR (JVM)
# Java Stream

Es un tipo de objeto nuevo en Java 1.8. Está en el paquete java.util.stream

Desde Java 1.8, todas las colecciones de JAVA (List, Map, Set...) tienen la función .stream()
que los convierte a un objeto de tipo Stream

El objeto de tipo Stream me permite aplicar programación funcional de tipo MAP REDUCE sobre una colección de datos.

Cuando vayamos a Spark, y tengamos hecho un programa usando Streams de Java, tan sólo tendremos que cambiar
la palabra Stream por la palabra RDD (que es el objeto equivalente que tiene Spark para trabajar con programación funcional)... y todo seguirá funcionando. (Más o menos)

## MAP REDUCE

Es una forma de procesar colecciones de datos.
Consta de 2 tipos de funciones:
- Funciones de tipo MAP?        Una función que aplico sobre una colección de datos
  y devuelve OTRA COLECCION DE DATOS
- Funciones de tipo REDUCE?     Una función que aplico sobre una colección de datos
  y devuelve ALGO QUE NO ES OTRA COLECCION DE DATOS

# Funciones tipo MAP

## Función map
                             doble
Stream<Integer>  ->  .map(   ALGO funcionDeMapeo(Integer dato)    )   ->    Stream<ALGO>
1                           x2                                              2
2                                                                           4
3                                                                           6
4                                                                           8

La función map me permite generar una nueva colección con los datos originales TRANSFORMADOS a través de una función de mapeo

## Función flatMap
## Función order
## Función filter
## Y otras 50

# Funciones tipo REDUCE

## Función reduce
## Función sum
## Función first
## Función collect
## Función forEach
## Y otras 50

