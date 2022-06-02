import Bmp.BmpFile;
import CommandLineArguments.CommandLineOptions;
import CommandLineArguments.EmbedArguments;
import CommandLineArguments.ExtractArguments;
import Encryption.Cipher;
import Steganography.Information;
import Steganography.LSB1;
import Steganography.LSB4;
import Steganography.LSBI;
import org.apache.commons.cli.*;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

public class Main {


    public static void main(String[] args) throws Exception {
        try {
            var parser = new DefaultParser();

            if (Arrays.stream(args).anyMatch(t -> t.equalsIgnoreCase("-embed"))) {
                var commandLine = parser.parse(CommandLineOptions.GetEmbedOptions(), args);
                var embedArguments = new EmbedArguments(commandLine);
                EmbedFile(embedArguments);

            } else {
                var commandLine = parser.parse(CommandLineOptions.GetExtractOptions(), args);
                var extractArguments = new ExtractArguments(commandLine);
                ExtractFile(extractArguments);
            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void EmbedFile(EmbedArguments args) throws Exception {
        var carrier = new BmpFile(args.CarrierFile);

        var rawInformation = Information.Load(args.InputFile).ToByteArray();

        if (args.UseEncyption())
            rawInformation =new Cipher(args.EncryptionPrimitive,args.ChainingMode).Encrypt(rawInformation, args.Password);

        var content = switch (args.SteganographyAlgorithm) {
            case LSB1 -> new LSB1().EmbedInformation(carrier, rawInformation);
            case LSB4 -> new LSB4().EmbedInformation(carrier, rawInformation);
            case LSBI -> new LSBI().EmbedInformation(carrier, rawInformation);
        };

        content.Save(args.OutputFile + ".bmp");
    }

    public static void ExtractFile(ExtractArguments args) throws Exception {

        var carrier = new BmpFile(args.CarrierFile);

        var rawInformation = switch (args.SteganographyAlgorithm) {
            case LSB1 -> new LSB1().ExtractInformation(carrier);
            case LSB4 -> new LSB4().ExtractInformation(carrier);
            case LSBI -> new LSBI().ExtractInformation(carrier);
        };

        if (args.UseEncryption())
            rawInformation = new Cipher(args.EncryptionPrimitive,args.ChainingMode).Decrypt(rawInformation, args.Password);

        var information = Information.Load(rawInformation);


        Files.write(new File(args.OutputFile + information.Extension).toPath(), information.Data);
    }
}
