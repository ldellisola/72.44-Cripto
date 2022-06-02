package Bmp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BmpPixel {
    public byte Red;
    public byte Green;
    public byte Blue;

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
