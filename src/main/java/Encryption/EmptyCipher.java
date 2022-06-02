package Encryption;

public class EmptyCipher implements CipherProperties {
    @Override
    public byte[] Decrypt(byte[] data, String password) {
        return new byte[0];
    }

    @Override
    public byte[] Encrypt(byte[] data) {
        return new byte[0];
    }
}
