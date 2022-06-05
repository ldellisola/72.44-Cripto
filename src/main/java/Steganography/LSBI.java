package Steganography;

import Bmp.BmpFile;
import org.bouncycastle.util.Arrays;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class LSBI implements Algorithm {

    public static void checkChanges(boolean[] b1, boolean[] b2, int[][] benefitMatrix){
        if(Arrays.areEqual(b1,b2))
            benefitMatrix[BitOperations.BitsToInt(b1)][0] ++;
        else
            benefitMatrix[BitOperations.BitsToInt(b1)][1] ++;
    }

    public static byte invInfo(byte pixel, int[][] benefitMatrix){
        var last_bits = BitOperations.ReadLastKBits(pixel, 2);
        int position_pattern = BitOperations.BitsToInt(last_bits);
        if(benefitMatrix[position_pattern][0] <= benefitMatrix[position_pattern][1]){
            pixel = BitOperations.WriteBit(pixel,0, !last_bits[1]);
        }
        return pixel;
    }

    private enum Pixel{
        Red, Green, Blue
    }

    @Override
    public BmpFile EmbedInformation(BmpFile carrier, byte[] data) throws Exception {
        if ((data.length + 4) * 8  > carrier.ContentInBytes.length)
            throw new Exception("Muy grande");

        var dataInBits = new boolean[data.length * 8 ];
        for (int i = 0; i < data.length; i++)
            System.arraycopy(BitOperations.ReadLastKBits(data[i], 8), 0, dataInBits, i * 8, 8);

        final var benefitMatrix = new int[4][2];
        for (int x = 0, i = 0, t = 0; x < carrier.ContentInBytes.length && t < 4; x++,i++){
            if (i < dataInBits.length){
                var beforeWrite = BitOperations.ReadLastKBits(carrier.ContentInBytes[x],2);
                carrier.ContentInBytes[x] = BitOperations.WriteBit(carrier.ContentInBytes[x] ,0, dataInBits[i]);
                var afterWrite = BitOperations.ReadLastKBits(carrier.ContentInBytes[x],2);
                checkChanges(beforeWrite,afterWrite,benefitMatrix);
            }
            else {
                var isInverted = benefitMatrix[t][0] <= benefitMatrix[t][1];
                carrier.ContentInBytes[x] = BitOperations.WriteBit(carrier.ContentInBytes[t], 0, isInverted);
                t++;
            }
        }

        for(int i = 0 ; i < dataInBits.length; i++)
            carrier.ContentInBytes[i] = invInfo(carrier.ContentInBytes[i], benefitMatrix);
        System.out.println(java.util.Arrays.deepToString(benefitMatrix));
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
// (tamaño|contenido|extension|0)|matriz
// 4 bytes del tamaño
// sacamos n bytes de contenido (definido por el tamaño)
// sacamos la extension hasta tener un byte en 0
// Viene la matriz
//
// (tamañoEnc|Enc(tamaño|contenido|extension|0))|matriz
//        int size = ByteBuffer.wrap(Arrays.copyOfRange(carrier.ContentInBytes,0,4)).getInt();
        int size = arrayToInt(Arrays.copyOfRange(carrier.ContentInBytes,0,4));
        int matrixPosition = searchMatrixPosition(carrier.ContentInBytes,size);
        var invVector = getInvVector(carrier.ContentInBytes, matrixPosition);
        System.out.println(java.util.Arrays.toString(invVector));
        for (var _byte : carrier.ContentInBytes) {
            WriteBit(BitOperations.ReadKBit(_byte, 0));
        }

        return Stream.toByteArray();
    }

    private int arrayToInt(byte[] copyOfRange) {
        int num = 0;
        for (int i = copyOfRange.length - 1; i >=0 ; i--) {
            num += copyOfRange[i] * Math.pow(10,copyOfRange.length - 1 - i);
        }
        return num;
    }

    private boolean [] getInvVector(byte[] contentInBytes,int matrixPosition) {
        var toReturn = new boolean[4];
        for (int i = 0; i < 4; i++) {
            toReturn[i] = BitOperations.ReadLastKBits(contentInBytes[matrixPosition+i],1)[0];
        }
        return toReturn;
    }

    private int searchMatrixPosition(byte[] contentInBytes, int size) {
        boolean flag = false;
        int i;
        for (i = size + 4; i < contentInBytes.length && !flag; i++) {
            flag = contentInBytes[i] == 0;
        }
        return i;
    }
}
