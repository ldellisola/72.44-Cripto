package Encryption;

import CommandLineArguments.Enums.ChainingModes;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public abstract class CipherAlgorithm {
    private final static int COUNT = 1;
    private final static int BYTE_SIZE = 4;

    abstract String getPrimitive();

    abstract int getKeyLength();

    abstract ChainingModes getChainingMode();

    public byte[] Decrypt(byte[] data, String password) throws Exception {
        var stream = new ByteArrayInputStream(data);
        int size = ByteBuffer.wrap(stream.readNBytes(BYTE_SIZE)).getInt();
        var encryptedData = stream.readNBytes(size);

        return transform(encryptedData, getChainingMode(), password, Cipher.DECRYPT_MODE);
    }

    public byte[] Encrypt(byte[] data, String password) throws Exception {
        var encryptedData = transform(data, getChainingMode(), password, Cipher.ENCRYPT_MODE);

        var stream = new ByteArrayOutputStream();

        stream.write(ByteBuffer.allocate(BYTE_SIZE).putInt(encryptedData.length).array());
        stream.write(encryptedData);

        return stream.toByteArray();
    }

    private byte[] transform(byte[] input, @NotNull ChainingModes mode, @NotNull String password, int cipherMode) throws Exception {
        Cipher cipher = Cipher.getInstance(getPrimitive() + "/" + mode + "/" + mode.getPadding());
        // Para la generación de clave a partir de una password, asumir que la función de hash usada es sha256, y que no se usa SALT.
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

        final byte[][] keyAndIV = EVP_BytesToKey(getKeyLength() / Byte.SIZE, cipher.getBlockSize(), sha256, (password).getBytes(StandardCharsets.UTF_8), COUNT);
        SecretKeySpec key = new SecretKeySpec(keyAndIV[0], getPrimitive());

        if (mode.usesIV()) {
            cipher.init(cipherMode, key, new IvParameterSpec(keyAndIV[1]));
        } else {
            cipher.init(cipherMode, key);
        }

        return cipher.doFinal(input);
    }

    // source: https://gist.github.com/luosong/5523434 & https://olabini.com/blog/tag/evp_bytestokey/
    public static byte[][] EVP_BytesToKey(int key_len, int iv_len, MessageDigest md, byte[] data, int count) {
        byte[][] both = new byte[2][];
        byte[] key = new byte[key_len];
        int key_ix = 0;
        byte[] iv = new byte[iv_len];
        int iv_ix = 0;
        both[0] = key;
        both[1] = iv;
        byte[] md_buf = null;
        int nkey = key_len;
        int niv = iv_len;
        int i;
        if (data == null) {
            return both;
        }
        int addmd = 0;
        for (; ; ) {
            md.reset();
            if (addmd++ > 0) {
                md.update(md_buf);
            }
            md.update(data);
            md_buf = md.digest();
            for (i = 1; i < count; i++) {
                md.reset();
                md.update(md_buf);
                md_buf = md.digest();
            }
            i = 0;
            if (nkey > 0) {
                for (; ; ) {
                    if (nkey == 0)
                        break;
                    if (i == md_buf.length)
                        break;
                    key[key_ix++] = md_buf[i];
                    nkey--;
                    i++;
                }
            }
            if (niv > 0 && i != md_buf.length) {
                for (; ; ) {
                    if (niv == 0)
                        break;
                    if (i == md_buf.length)
                        break;
                    iv[iv_ix++] = md_buf[i];
                    niv--;
                    i++;
                }
            }
            if (nkey == 0 && niv == 0) {
                break;
            }
        }
        for (i = 0; i < md_buf.length; i++) {
            md_buf[i] = 0;
        }
        return both;
    }
}
