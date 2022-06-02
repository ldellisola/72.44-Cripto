package Encryption;

public class AES256 extends AES {
    @Override
    int getKeyLength() {
        return 256;
    }
}
