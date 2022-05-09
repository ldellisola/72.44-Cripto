package Bmp;

public class InvalidBmpException extends Exception {
    public InvalidBmpException(String message) {
        super("The BMP File is not valid:" + message);
    }
}
