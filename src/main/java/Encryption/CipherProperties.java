package Encryption;

public interface CipherProperties {
    public byte[] Decrypt(byte[] data, String password);
    public byte[] Encrypt(byte[] data);
}
