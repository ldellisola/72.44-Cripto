package Steganography;

import Bmp.BmpFile;

import java.io.ByteArrayOutputStream;

public class LSB4 implements Algorithm {
    @Override
    public BmpFile EmbedInformation(BmpFile carrier, byte[] data) throws Exception {

        if (data.length * 8 > carrier.ContentInBytes.length * 4)
            throw new Exception("Muy grande");

        var dataInBits = new boolean[data.length * 8];

        for (int i = 0; i < data.length; i++) {
            System.arraycopy(BitOperations.ReadLastKBits(data[i], 8), 0, dataInBits, i * 8, 8);
        }

        for (int x = 0,i = 0; x < carrier.ContentInBytes.length && i < dataInBits.length; x++)
            for(int j=3; j>= 0;j--) {
                if( i >= dataInBits.length)
                    return carrier;
                carrier.ContentInBytes[x] = BitOperations.WriteBit(carrier.ContentInBytes[x], j, dataInBits[i++]);
            }

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

    public void WriteBits(boolean[] values) {
        for (var val : values)
            WriteBit(val);
    }

    @Override
    public byte[] ExtractInformation(BmpFile carrier) {

        for (var _byte: carrier.ContentInBytes)
            WriteBits(BitOperations.ReadLastKBits(_byte, 4));

        return Stream.toByteArray();
    }
}
