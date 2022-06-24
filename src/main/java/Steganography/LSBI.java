package Steganography;

import Bmp.BmpFile;
import org.bouncycastle.util.Arrays;

import java.io.ByteArrayOutputStream;



public class LSBI implements Algorithm {

    public static void checkChanges(boolean[] b1, boolean[] b2, int[][] benefitMatrix){
        if(Arrays.areEqual(b1,b2))
            benefitMatrix[BitOperations.BitsToInt(b1)][0] ++;
        else
            benefitMatrix[BitOperations.BitsToInt(b1)][1] ++;
    }

    public static byte invInfo(byte pixel, int[][] benefitMatrix){
        var last_bits = new boolean[4];
        last_bits[0] = BitOperations.ReadKBit(pixel,0);
        last_bits[1] = BitOperations.ReadKBit(pixel,1);
        int position_pattern = BitOperations.BitsToInt(last_bits);
        if(benefitMatrix[position_pattern][0] <= benefitMatrix[position_pattern][1]){
            pixel = BitOperations.WriteBit(pixel,0, !last_bits[0]);
        }
        return pixel;
    }


    @Override
    public BmpFile EmbedInformation(BmpFile carrier, byte[] data) throws Exception {
        if ((data.length  * 8 ) + 4 > carrier.ContentInBytes.length)
            throw new Exception("Muy grande");

        var dataInBits = new boolean[data.length * 8 ];
        for (int i = 0; i < data.length; i++)
            System.arraycopy(BitOperations.ReadLastKBits(data[i], 8), 0, dataInBits, i * 8, 8);

        final var benefitMatrix = new int[4][2];
        for (int x = 4, i = 0, t = 0; x < carrier.ContentInBytes.length && t < 4; x++,i++){
            if (i < dataInBits.length){
                var beforeWrite = new boolean[2];
                beforeWrite[0] = BitOperations.ReadKBit(carrier.ContentInBytes[x], 1);
                beforeWrite[1] = BitOperations.ReadKBit(carrier.ContentInBytes[x], 2);
                carrier.ContentInBytes[x] = BitOperations.WriteBit(carrier.ContentInBytes[x] ,0, dataInBits[i]);
                var afterWrite = new boolean[2];
                afterWrite[0] = BitOperations.ReadKBit(carrier.ContentInBytes[x], 1);
                afterWrite[1] = BitOperations.ReadKBit(carrier.ContentInBytes[x], 2);
                checkChanges(beforeWrite,afterWrite,benefitMatrix);
            }
            else {
                var isInverted = benefitMatrix[t][0] <= benefitMatrix[t][1];
                carrier.ContentInBytes[t] = BitOperations.WriteBit(carrier.ContentInBytes[t], 0, isInverted);
                t++;
            }
        }
        for(int i = 0 ; i < dataInBits.length; i++)
            carrier.ContentInBytes[i+4] = invInfo(carrier.ContentInBytes[i+4], benefitMatrix);
        return carrier;
    }

    private int CurrentBit = 0;
    public byte CurrentByte = 0;
    private final ByteArrayOutputStream Stream = new ByteArrayOutputStream();

    public void WriteBit(boolean value) {

        CurrentByte = BitOperations.WriteBit(CurrentByte, 7 - CurrentBit, value);
        CurrentBit = (CurrentBit + 1) % 8;

        if (CurrentBit == 0) {
            Stream.write(CurrentByte);
            CurrentByte = 0;
        }
    }

    @Override
    public byte[] ExtractInformation(BmpFile carrier) {
        var aux = new boolean[4];
        for (int i = 0; i < 4; i++) {
            aux[i] = BitOperations.ReadKBit(carrier.ContentInBytes[i],0);
        }
        for (int i = 4; i < carrier.ContentInBytes.length; i++) {
            var _byte = carrier.ContentInBytes[i];
            var last_two_bits = new boolean[2];
            last_two_bits[0] = BitOperations.ReadKBit(_byte,1);
            last_two_bits[1] = BitOperations.ReadKBit(_byte,2);
            boolean should_inv = aux[BitOperations.BitsToInt(last_two_bits)];
            var value = BitOperations.ReadKBit(_byte, 0);
            if(should_inv)
                value = !value;
            WriteBit(value);
        }
        return Stream.toByteArray();
    }

}
