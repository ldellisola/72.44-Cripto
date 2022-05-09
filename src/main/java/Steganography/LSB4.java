package Steganography;

import Bmp.BmpFile;

import java.io.ByteArrayOutputStream;

public class LSB4 implements Algorithm{
    @Override
    public BmpFile EmbedInformation(BmpFile carrier, byte[] data) {

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
