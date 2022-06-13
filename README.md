# stenography-crypto
Trabajo Práctico Especial para la materia 72.44 - Criptografía y Seguridad - Primer Cuatrimestre 2022.

1. [stenography-crypto](#stenography-crypto)
   1. [Autores](#autores)
2. [Instalación](#instalación)
   1. [Parámetros](#parámetros)
      1. [Ocultamiento](#ocultamiento)
      2. [Extracción](#extracción)
   2. [Ejemplos de uso](#ejemplos-de-uso)
      1. [Ejemplo 1](#ejemplo-1)
      2. [Ejemplo 2](#ejemplo-2)
      3. [Ejemplo 3](#ejemplo-3)

## Autores
* [Catalán, Roberto José](https://github.com/rcatalan98) - 59174
* [Dell'Isola, Lucas](https://github.com/ldellisola) - 58025
* [Pecile, Gian Luca](https://github.com/glpecile) - 59235

# Instalación
Como pre-requisito se requiere Java 17 y Maven instalados. 

Luego, situado en la carpeta raíz, realizar los siguientes comandos:
```sh
mvn clean install
java -jar ./target/stegobmp-jar-with-dependencies.jar <parametros>
```
Donde los `<parametros>` dependen de lo que se desea hacer y se especifican a continuación. 
## Parámetros
### Ocultamiento
| Parametro            | Descripción                                                                                   |
|----------------------|-----------------------------------------------------------------------------------------------|
| `-embed`             | Indica que se va a ocultar información.                                                       |
| `-in <file>`         | Archivo que se va a ocultar.                                                                  |
| `-p <bitmap_file>`   | Archivo bmp que será el portador.                                                             |
| `-out <bitmap_file>` | Archivo bmp de salida, es decir, el archivo bitmapfile con la información de file incrustada. |
| `-s <LSB1            | LSB4                                                                                          | LSBI>` | Algoritmo de esteganografiado: LSB de 1 bit, LSB de 4 bits, LSB Improved.  |
| `-a <aes128          | aes192                                                                                        | aes256 | des>` | Algoritmo de encriptación: AES de 128, 192 o 256 bits y DES de 64. |
| `-m <ecb             | cfb8                                                                                          | ofb | cbc>` | Modo de encriptación: CBC (IV y padding), ECB (padding), CFB8 (IV) y OFB (IV). |
| `-pass <password>`   | Indica la password encripción.                                                                |

### Extracción
| Parametro            | Descripción                                                                                   |
|----------------------|-----------------------------------------------------------------------------------------------|
| `-extract`           | Indica que se va a extraer información.                                                       |
| `-p <bitmap_file>`   | Archivo bmp que es el portador.                                                               |
| `-out <file>`        | Archivo de salida obtenido.                                                                   |
| `-out <bitmap_file>` | Archivo bmp de salida, es decir, el archivo bitmapfile con la información de file incrustada. |
| `-s <LSB1            | LSB4                                                                                          | LSBI>` | Algoritmo de esteganografiado: LSB de 1 bit, LSB de 4 bits, LSB Improved.  |
| `-a <aes128          | aes192                                                                                        | aes256 | des>` | Algoritmo de encriptación: AES de 128, 192 o 256 bits y DES de 64. |
| `-m <ecb             | cfb8                                                                                          | ofb | cbc>` | Modo de encriptación: CBC (IV y padding), ECB (padding), CFB8 (IV) y OFB (IV). |
| `-pass <password>`   | Indica la password encripción.                                                                |


## Ejemplos de uso
### Ejemplo 1
Embeber el archivo `pepe.png` en el archivo portador `blanco.bmp` mediante el método LSB1. obteniendo un archivo `pepe-blanco-LSB1.bmp`.
```sh
$stegobmp -embed -in "./assets/pepe.png" -p "./assets/blanco.bmp" -out "./assets/pepe-blanco-LSB1" -s LSB1
```
### Ejemplo 2
Extraer el archivo de texto `pepe-out` del archivo portador `pepe-blanco-LSB1.bmp` ocultado mediante el método LSB1.
```sh
$stegobmp -extract -p "./assets/pepe-blanco-LSB1.bmp" -out "./assets/pepe-out" -s LSB1
```
### Ejemplo 3
Extraer el archivo de texto `extract-LSBI-aes256-ofb.png` del archivo portador `ladoLSBIaes256ofb.bmp` ocultado mediante el algoritmo LSBI, con encripción AES256 en modo OFB con password “secreto”.
```sh
$stegobmp -extract -p "./assets/ladoLSBIaes256ofb.bmp" -out "./assets/extract-LSBI-aes256-ofb" -s LSBI -a AES256 -m OFB -pass "secreto"
```