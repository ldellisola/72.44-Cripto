# stenography-crypto

## Integrantes
* [Catalán, Roberto José](https://github.com/rcatalan98) - 59174
* [Dell'Isola, Lucas](https://github.com/ldellisola) - 58025
* [Pecile, Gian Luca](https://github.com/glpecile) - 59235

## Ejemplos de uso
Esteganografiar el archivo de texto `mensaje1.txt` en el archivo portador `imagen1.bmp` obteniendo un archivo `imagenmas1.bmp` mediante el algoritmo LSB Improved, con encripción DES en modo CBC con password “oculto”
```sh
$stegobmp -embed -in "mensaje1.txt" -p "test.bmp" -out "imagenmas1" -s "LSB1"
```
Extraer el archivo de texto `mensaje1.txt` del archivo portador `imagenmas1.bmp` ocultado mediante el algoritmo LSB Improved, con encripción DES en modo CBC con password “oculto”
```sh
$stegobmp -extract -p "imagenmas1.bmp" -out "mensaje1" -s "LSB1"
```