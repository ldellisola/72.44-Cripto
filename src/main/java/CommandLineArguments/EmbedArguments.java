package CommandLineArguments;

import CommandLineArguments.Enums.ChainingModes;
import CommandLineArguments.Enums.EncryptionPrimitives;
import CommandLineArguments.Enums.SteganographyAlgorithms;
import org.apache.commons.cli.CommandLine;

import java.io.File;
import java.io.FileNotFoundException;

public class EmbedArguments {
    public final File InputFile;
    public final File CarrierFile;
    public final String OutputFile;
    public final SteganographyAlgorithms SteganographyAlgorithm;
    public final EncryptionPrimitives EncryptionPrimitive;
    public final ChainingModes ChainingMode;
    public final String Password;


    public EmbedArguments(CommandLine cmd) throws Exception {
        this.InputFile = new File(cmd.getOptionValue("in"));

        if (!this.InputFile.exists() || this.InputFile.isDirectory())
            throw new FileNotFoundException(cmd.getOptionValue("in"));

        this.CarrierFile = new File(cmd.getOptionValue("p"));

        if (!this.CarrierFile.exists() || this.CarrierFile.isDirectory())
            throw new FileNotFoundException(cmd.getOptionValue("p"));

        this.OutputFile = cmd.getOptionValue("out");

        try {
            this.SteganographyAlgorithm = SteganographyAlgorithms.valueOf(cmd.getOptionValue("steg"));
        } catch (IllegalArgumentException e) {
            throw new Exception(cmd.getOptionValue("steg") + " is not a valid Steganography Algorithm");
        }

        try {
            this.EncryptionPrimitive = EncryptionPrimitives.valueOf(cmd.getOptionValue("a", EncryptionPrimitives.AES128.name()));
        } catch (IllegalArgumentException e) {
            throw new Exception(cmd.getOptionValue("a") + " is not a valid Encryption primitive");
        }

        try {
            this.ChainingMode = ChainingModes.valueOf(cmd.getOptionValue("m", ChainingModes.CBC.name()));
        } catch (IllegalArgumentException e) {
            throw new Exception(cmd.getOptionValue("m") + " is not a valid chaining mode");
        }

        this.Password = cmd.getOptionValue("pass");
    }


    public Boolean UseEncyption() {
        return this.Password != null;
    }
}
