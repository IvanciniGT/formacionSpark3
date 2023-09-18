# Java

Es un lenguaje que tiene una gran cantidad de CAGADAS en su sintaxis.
Además, ha sufrido de problemas de índole "política".

## Historia

Java lo fabricaba una empresa llamada: SUN MICROSYSTEMS (empresa querida por todos los programadores)
En un momento dado, SUN MICROSYSTEMS fue comprada por ORACLE (empresa odiada por todos los programadores)
Oracle es una empresa especializada:
- Software de gestión de BBDD... no hay otra empresa que tenga un producto mejor de BBDD que Oracle
- Destrozar todo lo que toca que no sea su BBDD:
  - MySQL: La antigua BBDD reina en el mundo opensource... la compró Oracle y la dejó de lado -> MariaDB
  - Hudson: La antigua herramienta de integración continua ... la compró Oracle y la dejó de lado -> Jenkins
  - OpenOffice: La antigua herramienta de ofimática opensource ... la compró Oracle y la dejó de lado -> LibreOffice
  - Java. Lo dejaron de lado... hubo muchas peleas "políticas" (google)

La versión 1.6 de Java sale en 2006 \
La versión 1.7 de Java sale en 2011  > En 8 años solo sacaron 2 versiones de JAVA
La versión 1.8 de Java sale en 2014 / 
En la versión 1.9, se libera la máquina virtual de JAVA (JVM) para que cualquiera pueda hacer su propia máquina virtual de JAVA
Antes teníamos que usar la JVM de Oracle si o si... ahora podemos usar la JVM de Oracle o la JVM de OpenJDK, Amazon Corretto, Eclipse Temurin, etc...

## Alternativas al lenguaje JAVA

Surgen muchos lenguajes que se ejecutan en la JVM, compilando a byte-code de JAVA, pero ofreciendo una sintaxis más moderna y agradable:
- Scala
- Kotlin

## Todo el mundo Bigdata

Se ha programado en Scala.. Apache Spark está escrito en Scala.

Un problema con Scala es que hay 2 versiones incompatibles entre sí: Scala 2.12 y Scala 2.13
La gente que hace Apache Spark, ofrece 2 distribuciones de cada versión: Scala 2.12 y Scala 2.13


# 
--add-opens=java.base/java.lang=ALL-UNNAMED 
--add-opens=java.base/java.lang.invoke=ALL-UNNAMED 
--add-opens=java.base/java.lang.reflect=ALL-UNNAMED 
--add-opens=java.base/java.io=ALL-UNNAMED 
--add-opens=java.base/java.net=ALL-UNNAMED 
--add-opens=java.base/java.nio=ALL-UNNAMED 
--add-opens=java.base/java.util=ALL-UNNAMED 
--add-opens=java.base/java.util.concurrent=ALL-UNNAMED 
--add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED 
--add-opens=java.base/sun.nio.ch=ALL-UNNAMED 
--add-opens=java.base/sun.nio.cs=ALL-UNNAMED 
--add-opens=java.base/sun.security.action=ALL-UNNAMED 
--add-opens=java.base/sun.util.calendar=ALL-UNNAMED 
--add-opens=java.security.jgss/sun.security.krb5=ALL-UNNAMED

# Qué era UNIX?

Un Sistema operativo de los Lab. Bell de AT&T

# Qué es UNIX?

Hoy en día es una especificación (realmente son 2 espec: SUS + POSIX) acerca de cómo debe implementarse un Sistema Operativo.

Muchas empresas crean sus propios Sistema Operativos... y lo hacen siguiendo ese estándar.

Tenemos sistemas operativos UNIX hoy en día, que son aquellos que cumplen con esa especificación.

- ORACLE (Exadata) Tiene su propio SO, que cumple con la especificación de UNIX: Solaris (Unix® Certified)
- HP (Hewlett Packard) Tiene su propio SO, que cumple con la especificación de UNIX: HP-UX (Unix® Certified)
- IBM (International Business Machines) Tiene su propio SO, que cumple con la especificación de UNIX: AIX (Unix® Certified)
- APPLE (Apple Computer) Tiene su propio SO, que cumple con la especificación de UNIX: MacOS (Unix® Certified)

# GNU/Linux (Redhat, Suse, Debian, Ubuntu...)

Es SO que supuestamente cumple con la especificación de UNIX.

GNU: GNU is Not Unix ( Y esto lo hicieron para poder usar el nombre UNIX sin tener que pagarle a AT&T )
BSD386: Berkeley Software Distribution (386) Éstos si dijeron que tenían un SO UNIX... Y llego AT&T y les metió un paquete gordo


---

# Vamos a montar el sistema de Trending Topics de Twitter (X)

Vamos a recibir un montón de tweets (textos).
Por ahora, fijos... los meteis en código, una lista de textos:

  "En la playa con mis amigos #SummerLove#GoodVibes"
  "Mierda de verano... estudiando... #cacaDeVerano"
  "En la playa con mi familia #SummerLove"
  "Sacando a mi perro #DogsLove"

Al final del recorrido, obtener los hastags más usados.
Cuidado... igual que vamos a tener una lista con los textos, vamos a tener otra lista con palabras prohibidas en los hashtags:
  culo
  pedo
  caca
  pis
  mierda

Queremos los 3 más usados... y el número de veces que aparecen.

Lista con Hashtags prohibidos
---------------------------------------------------
  "caca"
  "culo"
  "pedo"
  "pis"

Lista (RDD | Stream)                                                      Lista (RDD|Stream)
---------------------------------------------------         ===>          ---------------------------------------------------
"En la playa con mis amigos #SummerLove#GoodVibes"                        "GoodVibes",  2
"Mierda de verano... estudiando... #CacaDeVerano"                         "SummerLove", 1
"En la playa con mi familia #GoodVibes"                                   "DogsLove",   1
"Sacando a mi perro #DogsLove"                                            


-----
Algoritmo:
Quedarnos solo con los hashtags... pero esto necesitará me temo ... unas cuantas operaciones:
- En cada tweet/texto:
  - Si separamos las palabras/entidades/vocablos/hashtags que hay dentro

"En la playa con mis,amigos...(y super-familia)#SummerLove#GoodVibes"

"En"
"la"
"playa"
"con"
"mis"
"amigos"
"#SummerLove"
"#GoodVibes"

PASO 1: .replaceAll("#"," #")
PASO 2: .split("[ .,()_;:¡!¿?@<>-]")
PASO 3: filtrar . Me quedo con los que empiezan por "#"

"#SummerLove"
"#GoodVibes"
"#cacaDeVerano"
"#GoodVibes"
"#DogsLove"

PASO 4: Convertirlo a mayúsculas o minúsculas
"#summerlove"
"#goodvibes"
"#cacadeverano"
"#goodvibes"
"#dogslove"

PASO 5: Filtrar los hashtag que no quiero (los que contengan alguna de las palabras prohibidas)
"#summerlove"
"#goodvibes"
"#goodvibes"
"#dogslove"
--- A ver si al menos llegáis hasta aquí

PASO 6: Agrupar por los hashtags y contar
"#summerlove", 1
"#goodvibes", 2
"#dogslove", 1

PASO 7: Ordeno por cantidad

PASO 8: Me quedo con los primeros (3) <--- Mi función de reducción


-------
Lista de textos                                            Lista de Listas de textos
"En la playa con mis amigos #SummerLove#GoodVibes"      -> flatMap(SPLIT) ->    ["En", "la", "playa", "con", "mis", "amigos", "#SummerLove", "#GoodVibes"]
"Mierda de verano... estudiando... #cacaDeVerano"                           ["Mierda", "de", "verano", "estudiando", "#cacaDeVerano"]
"En la playa con mi familia #SummerLove"                                    ["En", "la", "playa", "con", "mi", "familia", "#SummerLove"]
"Sacando a mi perro #DogsLove"                                              ["Sacando", "a", "mi", "perro", "#DogsLove"]

-------

[ "caca", "pedo" ]
"cacaDeVeranoPedo"

La quiero quitar... algoritmo

Necesito una función que devuelva true cuando: 

```java
class TEST {
  public boolean filtarHashtagsIndeseados(String hashtag) {
    // Necesito asegurarme que ninguna palabra de la lista está en el hashtag 
    for (String palabra : listaPalabrasIndeseadas) {
      if (hashtag.contains(palabra)) {
        return false;
      }
    }
    return true;
  }
  public boolean filtarHashtagsIndeseados2(String hashtag) {
    // Necesito asegurarme que ninguna palabra de la lista está en el hashtag 
    listaPalabrasIndeseadas.stream().filter( palabraIndeseada -> hashtag.contains(palabraIndeseada) ).count() == 0;
  }
}
```

"caca" Si me queda al menos 1... la tengo que quitar
"pedo"
-------