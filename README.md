# stenography-crypto
Trabajo Práctico Especial para la materia 72.44 - Criptografía y Seguridad - Primer Cuatrimestre 2022.

1. [stenography-crypto](#stenography-crypto)
   1. [Autores](#autores)
   2. [Ejemplos de uso](#ejemplos-de-uso)

## Autores
* [Catalán, Roberto José](https://github.com/rcatalan98) - 59174
* [Dell'Isola, Lucas](https://github.com/ldellisola) - 58025
* [Pecile, Gian Luca](https://github.com/glpecile) - 59235

## Ejemplos de uso
Embeder el archivo de texto `mensaje1.txt` en el archivo portador `testOutput.bmp` obteniendo un archivo `test1.bmp` mediante el algoritmo LSB1, con encripción DES en modo CBC con password “oculto”
```sh
$stegobmp -embed -in "mensaje1.txt" -p "test1.bmp" -out "testOutput" -s LSB1 -a DES -m CBC -pass "oculto"
```
Extraer el archivo de texto `mensaje1.txt` del archivo portador `testOutput.bmp` ocultado mediante el algoritmo LSB1, con encripción DES en modo CBC con password “oculto”
```sh
$stegobmp -extract -p "testOutput.bmp" -out "mensaje1" -s LSB1 -a DES -m CBC -pass "oculto"
```