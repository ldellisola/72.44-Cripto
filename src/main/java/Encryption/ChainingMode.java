package Encryption;

public interface ChainingMode {
    String getName();

    String getPadding();

    boolean usesIV();
}
