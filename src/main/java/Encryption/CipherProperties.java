package Encryption;

import CommandLineArguments.Enums.ChainingModes;
import CommandLineArguments.Enums.EncryptionPrimitives;
import java.util.Locale;

public class CipherProperties extends CipherAlgorithm {
    private final String encryptionPrimitive;
    private final int keySize;
    private final String chainingMode;

    public CipherProperties(EncryptionPrimitives encryptionPrimitive, ChainingModes chainingMode) {
        this.encryptionPrimitive = encryptionPrimitive.toString();
        this.chainingMode = chainingMode.toString();
        this.keySize = encryptionPrimitive.KeyLength();
    }

    @Override
    String getName() {
        return encryptionPrimitive;
    }

    @Override
    int getKeyLength() {
        return keySize;
    }

    public String chainingMode() {
        return chainingMode;
    }

    @Override
    ChainingModes getChainingMode() {
        return ChainingModes.valueOf(chainingMode.toUpperCase(Locale.ROOT));
    }
}
