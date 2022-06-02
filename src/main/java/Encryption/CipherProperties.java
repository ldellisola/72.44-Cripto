package Encryption;

import CommandLineArguments.Enums.ChainingModes;
import CommandLineArguments.Enums.EncryptionPrimitives;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;

public class Cipher {
    private final String encryptionPrimitive;
    private final int keySize;
    private final String chainingMode;

    public Cipher(EncryptionPrimitives encryptionPrimitive, ChainingModes chainingMode) {
        this.encryptionPrimitive = encryptionPrimitive.toString();
        this.chainingMode = chainingMode.toString();
        this.keySize = encryptionPrimitive.KeyLength();

    }
    public byte[] Decrypt(byte[] data, String password) throws Exception {

        var cipher = javax.crypto.Cipher.getInstance(encryptionPrimitive+"/"+chainingMode+"/PKCS5Padding");

        var key = new SecretKeySpec(password.getBytes(),encryptionPrimitive);

        cipher.init(javax.crypto.Cipher.DECRYPT_MODE,key);

        SecretKeyFactory.getInstance("").generateSecret(null);

        return cipher.doFinal(data);
    }

    public byte[] Encrypt(byte[] data, String password) throws Exception {

        var cipher = javax.crypto.Cipher.getInstance(encryptionPrimitive+"/"+chainingMode+"/PKCS5Padding");

        var key = new SecretKeySpec(password.getBytes(),encryptionPrimitive);


        cipher.init(javax.crypto.Cipher.DECRYPT_MODE,key);

        return cipher.doFinal(data);

    }
}
