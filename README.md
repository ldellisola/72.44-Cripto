# stenography-crypto
Trabajo Práctico Especial para la materia 72.44 - Criptografía y Seguridad - Primer Cuatrimestre 2022.

1. [stenography-crypto](#stenography-crypto)
   1. [Autores](#autores)
2. [Instalación](#instalación)
   1. [Ejemplos de uso](#ejemplos-de-uso)

## Autores
* [Catalán, Roberto José](https://github.com/rcatalan98) - 59174
* [Dell'Isola, Lucas](https://github.com/ldellisola) - 58025
* [Pecile, Gian Luca](https://github.com/glpecile) - 59235

# Instalación
Como pre-requisito se requiere Java 17 y Maven instalados. 

Luego, situado en la carpeta raíz, realizar los siguientes comandos:
```
mvn clean install
java -jar ./target/stegobmp-jar-with-dependencies.jar 
```

## Ejemplos de uso
### Ejemplo 1
Embeber el archivo de texto `mensaje1.txt` en el archivo portador `testOutput.bmp` obteniendo un archivo `test1.bmp` mediante el algoritmo LSB1, con encripción DES en modo CBC con password “oculto”.
```sh
$stegobmp -embed -in "mensaje1.txt" -p "test1.bmp" -out "testOutput" -s LSB1 -a DES -m CBC -pass "oculto"
```
### Ejemplo 2
Extraer el archivo de texto `mensaje1.txt` del archivo portador `testOutput.bmp` ocultado mediante el algoritmo LSB1, con encripción DES en modo CBC con password “oculto”.
```sh
$stegobmp -extract -p "testOutput.bmp" -out "mensaje1" -s LSB1 -a DES -m CBC -pass "oculto"
```
### Ejemplo 3
Extraer el archivo de texto `extract-LSBI-aes256-ofb.png` del archivo portador `ladoLSBIaes256ofb.bmp` ocultado mediante el algoritmo LSBI, con encripción AES256 en modo OFB con password “secreto”.
```sh
$stegobmp -extract -p "./assets/ladoLSBIaes256ofb.bmp" -out "./assets/extract-LSBI-aes256-ofb" -s LSBI -a AES256 -m OFB -pass "secreto"
```
### Ejemplo 4
Extraer el archivo `back-LSB4-extract.png` del portador `back.bmp`.
```sh
$stegobmp -extract -p "./assets/back.bmp" -out "./assets/back-LSB4-extract" -s LSB4
```