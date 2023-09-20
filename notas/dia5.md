Imaginad que tengo que leer 2 archivos con DNIs
Y quiero cruzar los datos.

Procesamiento

Imaginad que tengo que guardar un archivo con DNIS

De seguro, esos DNIs me vienen como TEXTO: 12345678T

Cuánto ocupa un DNI en DISCO?
 9 caracteres
Cada carácter de un DNI ocupa 1 byte (al guardarlo en un determinado encoding). ASCII, UTF-8, ISO-8859-1
 El DNI va a ocupar 9 bytes

Pero... podría guardar el DNI como un número entero... sin letra... una vez validado.
En el futuro, si me hace falta, regenero la letra.

Cuánto ocupa un DNI como número?
1 byte = 256 valores / números
2 bytes = 65000 = 256x256 /números
4 bytes = 256 * 256 * 256 * 256  = + de 4100.000.000 /números
    En 4 bytes me entran los dnis de toda la unión europea

Al guardar los dnis como números, me ocupan menos de la mitad. Aún guardando la letra a parte, sería un byte más
5 bytes frente a 9 bytes de guardarlo como texto.

Disco duro de 10M
Hoy en día son de 10 Tbs = 1000 G = 1.000.000 M

1 Millón de DNIs ( cada día)  y cada DNI me ocupa 9 bytes = 9.000.000 bytes = 9 Megas x 3 = 27 Megas al día x 3 años...= 30 Gb
1 Millón de DNIs y cada DNI me ocupa 5 bytes = 5.000.000 bytes = 5 Megas x 3 = 15

Los datos se guardan por triplicado

Cualquier programa que haga necesitará leer el doble de bytes del HDD, meter en memoria el doble de tamaño,
Las comparaciones (Este DNI = este otro DNI) van a ir mucho mas lentas... tengo que guardar el doble de bytes en HDD
Tengo que mandar por red el doble de bytes.

OTRO EJEMPLO SON LAS FECHAS 
---

Tanto Python como Java (que son los lenguajes desde los que ejecutamos Spark) 
son lenguajes que hacen un DESTROZO enrome en la memoria RAM.
Aunque trabaje desde python. Al final, todo se ejecuta en JAVA... y eso hace que si me pongo a crear muchas cosas en RAM
el programa vaya muy lento.