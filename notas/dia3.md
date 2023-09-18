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