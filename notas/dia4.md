    Stream/RDD de entrada                                                                       Stream/RDD de salida
    ---------------------------------------------------         ===>          ---------------------------------------------------   
                  map                       reduceByKey(suma sobre los valores)   map                     sortByKey
    summerlove     ->    Tuple2< summerlove , 1 >  --> Tuple2< summerlove , 1 > --> Tuple2< 1, summerlove > Tuple2< 2, goodvibes  > 
    goodvibes      ->    Tuple2< goodvibes  , 1 >  --> Tuple2< goodvibes  , 2 > --> Tuple2< 2, goodvibes  > Tuple2< 1, summerlove >            
    goodvibes      ->    Tuple2< goodvibes  , 1 >  --> Tuple2< dogslove   , 1 > --> Tuple2< 1, dogslove   > Tuple2< 1, dogslove   >
    dogslove       ->    Tuple2< dogslove   , 1 >  
                                    ^ Key (No es el key de un Map (no es único)
RDD<ObjetoPropio>

ObjetoPropio
    String hashtag
    int    contador

Es un objeto de Spark, que me permite guardar 2 datos de tipos diferentes
RDD<Tuple2<String, Integer>> -> PairRDD<String, Integer>

Al trabajar con esos PairRDD tenemos un montón de funciones adicionales:
- Agrupame por uno de los datos
- Cuenta por uno de los datos
- Ordena por uno de los datos

Colección de VALORES, donde cada valor contiene 2 datos
El RDD no es un MAP... en el sentido de que:
- Los String que estamos guardando no son claves, por tanto pueden aparecer duplicados
- No puedo usar esos strings para acceder a un valor (número) del RDD, solo puedo iterar sobre todos los elementos del RDD

Map: Colección de VALORES donde cada valor lleva asociado una clave que lo identifica
     Map<String, Integer>
     Conceptualmente es una LISTA / SET
    miMapa.get("clave")

Lista: Colección de VALORES donde cada valor lleva asociado una clave que lo identifica... Siendo la clave la posición en la lista
    miLista.get(i)

     Map<Integer, Integer>

---

# Kafka

Es un sistema de mensajería, equivalente a ActiveMQ, RabbitMQ
Todos los días, a todas horas usais un sistema de mensajería: Whatsapp, Telegram, Signal, Facebook Messenger, etc...

Los sistemas de mensajería permiten comunicaciones ASINCRONAS

Llamada de teléfono: Comunicación Sincrona
    - Yo llamo a alguien
    - Espero a que me conteste
    - Hablamos
    - Cuelgo
    - Si quiero volver a hablar con esa persona, tengo que volver a llamarla
    - * Qué pasa si la persona no está disponible en ese momento? La llamada se pierde

Mensaje de Whatsapp: Comunicación Asincrona
    - Yo envío un mensaje al servidor de Whatsapp
    - El servidor de Whatsapp lo guarda en una cola (cola de mensajes que podemos usar solo yo y a quién se lo he enviado)
        En el servidor de mensajería tenemos miles de millones de colas
    - Y recibo confirmación         √               Me quedo tranquilo. No importa si la otra persona no está operativa en ese momento... le llegará
    - En cuanto la otra persona se conecte, recibirá el mensaje... y yo recibo el           √√

Esto ocurre también entre procesos informáticos... que deben hablar entre sí.
Y el problema es que uno de ellos en un momento dado no está disponible... y el otro no puede esperar a que esté disponible para mandarle el mensaje 
En ese caso usamos una herramienta tipo Kafka, que actúa de garante de que el mensaje llegará a su destino.

Netflix es un sistema informático muy complejo... que tiene que comunicar muchos procesos entre sí.

---

Vuelvo a tweeter (X)

Escribo un tweet en una app que se ejecuta en un navegador o un teléfono
De ahí, el programa que ejecuta esa app envía el tweet a una cola de mensajería:
Tweets recibidos y ahí se queda:
    
    T1 < T2 < T3 < T4 Y voy a poner mi mejor esfuerzo en que KAFKA no se caiga... y me lo pone fácil aquello. 

Y luego tengo a programas que leen de ahí... de esa cola (Como si fuera un grupo de Whatsapp)
    - Programa que lee de la cola y lo publica en mi muro
    - Programa que valida que el mensaje cumpla con las normas de Twitter
    - Quizás mi tweet lleva una foto... y hay un programa que se encarga de transformar esa foto a un tamaño adecuado para que se vea bien en el muro   
    - Quizás mi tweet lleva foto.... y hay un programa que entra y hace reconocimiento facial para taggear a las personas que salen en la foto
    - Y tengo otro programa pre-procesa los hashtags... y los mete en otra cola de mensajería: TEMAS CALENTITOS H1 < H2 < H1 < H3    (2)
        Y tengo programado que cada 30 minutos entre otro programa a totalizar los hashtags que hay en esa cola... (1)
            y me diga cuales son los más usados -> Calcule los trending topics
    - Y tengo otro programa que analiza las menciones... y deje en otra cola los datos preprocesados
            , para que otro programa los analice y me diga cuales son los usuarios más mencionados
            o manda mensajes a los usuarios que han sido mencionados en un tweet

Es muy habitual en Spark, lanzar procesos BATCH, como nuestro ejemplo o como el ejemplo (1)
Pero también es muy habitual lanzar procesos STREAMING... que son procesos que se ejecutan continuamente (2)

En Spark tenemos una librería que es capaz de ayudarme a montar procesos en STREAMING
Y esa librería viene con funcionalidades para ayudarme a recibir cosas por un puerto... o leyéndolas de un Kafka

Por contra, cuando trabajamos con procesos Batch, lo que hacemos no es leer cosas de un Kafka ( o si)...
Pero también leer cosas de un fichero, de una BBDD, de un API REST, de un FTP, de un SFTP, de un S3, de un HDFS, de un Azure Blob Storage, de un Google Cloud Storage, etc...