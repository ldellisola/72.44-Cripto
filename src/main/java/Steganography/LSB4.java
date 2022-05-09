package Steganography;

import Bmp.BmpFile;

import java.io.ByteArrayOutputStream;

public class LSB4 implements Algorithm{
    @Override
    public BmpFile EmbedInformation(BmpFile carrier, byte[] data) throws Exception {

        if (data.length * 8 > carrier.Content.length * carrier.Header.BytesPerPixel()*4)
            throw new Exception("Muy grande");

        var dataInBits = new boolean[data.length * 8];

        for (int i = 0; i < data.length; i++) {
            System.arraycopy(BitOperations.ReadLastKBits(data[i],8),0,dataInBits,i * 8,8);
        }

        int i =0;
        for (var pixel: carrier.Content){

            for (int j = 3; j >=0; j--) {
                if (i >= dataInBits.length)
                    return carrier;
                pixel.Blue = BitOperations.WriteBit(pixel.Blue,j,dataInBits[i++]);
            }

            for (int j = 3; j >=0; j--) {
                if (i >= dataInBits.length)
                    return carrier;
                pixel.Green = BitOperations.WriteBit(pixel.Green,j,dataInBits[i++]);
            }

            for (int j = 3; j >=0; j--) {
                if (i >= dataInBits.length)
                    return carrier;
                pixel.Red = BitOperations.WriteBit(pixel.Red,j,dataInBits[i++]);
            }
        }

        return carrier;
    }

    private int CurrentBit = 0;
    public byte CurrentByte = 0;
    private final ByteArrayOutputStream Stream = new ByteArrayOutputStream();

    public void WriteBit(boolean value){

        CurrentByte = BitOperations.WriteBit(CurrentByte,7-CurrentBit,value);
        CurrentBit = (CurrentBit + 1) % 8;

        if (CurrentBit == 0){
            Stream.write(CurrentByte);
            CurrentByte = 0;
        }
    }

    public void WriteBits(boolean[] values){
        for (var val: values)
            WriteBit(val);
    }

    @Override
    public byte[] ExtractInformation(BmpFile carrier) {
        for (var pixel : carrier.Content) {
            WriteBits(BitOperations.ReadLastKBits(pixel.Blue, 4));
            WriteBits(BitOperations.ReadLastKBits(pixel.Green, 4));
            WriteBits(BitOperations.ReadLastKBits(pixel.Red, 4));
        }

        return Stream.toByteArray();
    }
}
