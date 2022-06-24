package Bmp;

public class BmpPixel {
    public final byte Red;
    public final byte Green;
    public final byte Blue;

    public BmpPixel(byte red, byte green, byte blue) {
        Red = red;
        Green = green;
        Blue = blue;
    }

    public BmpPixel(byte[] data) {
        Red = data[2];
        Green = data[1];
        Blue = data[0];
    }




    public byte[] ToByteArray() {
        return new byte[]{Blue, Green, Red};
    }

    @Override
    public String toString() {
        return "(R: " + Byte.toUnsignedInt(Red) + " G: " + Byte.toUnsignedInt(Green) + " B: " + Byte.toUnsignedInt(Blue) + ")";
    }
}
