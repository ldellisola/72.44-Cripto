package Encryption;

public class DES extends CipherAlgorithm {
    @Override
    String getName() {
        return "DES";
    }

    @Override
    int getKeyLength() {
        return 64;
    }
}
