package Encryption;

import CommandLineArguments.Enums.ChainingModes;
import CommandLineArguments.Enums.EncryptionPrimitives;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class CipherProperties extends CipherAlgorithm {
    private final String encryptionPrimitive;
    private final int keySize;
    private final String chainingMode;

    public CipherProperties(@NotNull EncryptionPrimitives encryptionPrimitive, @NotNull ChainingModes chainingMode) {
        this.encryptionPrimitive = encryptionPrimitive.toString();
        this.chainingMode = chainingMode.toString();
        this.keySize = encryptionPrimitive.KeyLength();
    }

    @Override
    String getPrimitive() {
        return encryptionPrimitive;
    }

    @Override
    int getKeyLength() {
        return keySize;
    }

    @Override
    ChainingModes getChainingMode() {
        return ChainingModes.valueOf(chainingMode.toUpperCase(Locale.ROOT));
    }
}
