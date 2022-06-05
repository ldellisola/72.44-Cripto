package Encryption;

import CommandLineArguments.Enums.ChainingModes;
import CommandLineArguments.Enums.EncryptionPrimitives;

public class EmptyCipher extends CipherProperties {
    public EmptyCipher(EncryptionPrimitives encryptionPrimitive, ChainingModes chainingMode) {
        super(encryptionPrimitive, chainingMode);
    }
    @Override
    public byte[] Decrypt(byte[] data, String password) {
        return new byte[0];
    }

//    @Override
//    public byte[] Encrypt(byte[] data) {
//        return new byte[0];
//    }
}
