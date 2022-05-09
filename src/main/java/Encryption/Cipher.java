package Encryption;

public interface Cipher {
    public byte[] Decrypt(byte[] data, String password);
    public byte[] Encrypt(byte[] data);
}
