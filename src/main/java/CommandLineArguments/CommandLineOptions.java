package CommandLineArguments;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class CommandLineOptions {
    static public Options GetEmbedOptions() {
        return new Options()
                .addRequiredOption("embed", "embed", false, "Indica que se va a ocultar información")
                .addRequiredOption("in", "in", true, "Archivo que se va a ocultar")
                .addRequiredOption("p", "p", true, "Archivo bmp que será el portador")
                .addRequiredOption("out", "out", true, "Archivo bmp de salida, es decir, el archivo bitmapfile con la información de file incrustada")
                .addRequiredOption("s", "steg", true, "algoritmo de esteganografiado: LSB de 1bit (LSB1), LSB de 4 bits (LSB4), LSB Enhanced (LSBI)")
                .addOption("a", true, "Agloritmo utilizado")
                .addOption("m", true, "Encadenamiento utilizado")
                .addOption("pass", "pass", true, "password");
    }


    static public Options GetExtractOptions() {
        return new Options()
                .addRequiredOption("extract", "extract", false, "Indica que se va a extraer información")
                .addRequiredOption("p", "p", true, "Archivo bmp portador")
                .addRequiredOption("out", "out", true, "Archivo de salida obtenido")
                .addRequiredOption("s", "steg", true, "algoritmo de esteganografiado: LSB de 1bit (LSB1), LSB de 4 bits (LSB4), LSB Enhanced (LSBI)")
                .addOption("a", true, "Agloritmo utilizado")
                .addOption("m", true, "Encadenamiento utilizado")
                .addOption("pass", "pass", true, "password");
    }

}
