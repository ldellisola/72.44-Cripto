package Steganography;

public class BitOperations {

    public static boolean ReadKBit(byte n, int pos){

        return (n & (0b00000001 << pos)) != 0;
    }

    public static boolean[] ReadLastKBits(byte n, int k) {
        var retValue = new boolean[k];

        for (int i = k-1; i >=0; i--) {
            retValue[k-1-i] = ReadKBit(n,i);
        }

        return retValue;
    }

    public static byte WriteBit(byte src, int pos, boolean value){
        if (value){
            return (byte) (src | (0b00000001 << pos));
        }
        else {
            return  (byte) (src & ~(0b00000001 << pos));
        }
    }




}
