package Steganography;

import Bmp.BmpFile;

import java.io.ByteArrayOutputStream;

public class LSB1 implements Algorithm {
    @Override
    public BmpFile EmbedInformation(BmpFile carrier, byte[] data) throws Exception {

        if (data.length * 8 > carrier.ContentInBytes.length)
            throw new Exception("Muy grande");

        var dataInBits = new boolean[data.length * 8];
        for (int i = 0; i < data.length; i++) {
            System.arraycopy(BitOperations.ReadLastKBits(data[i], 8), 0, dataInBits, i * 8, 8);
        }

        for (int x = 0,i = 0; x < carrier.ContentInBytes.length && i < dataInBits.length; x++, i++)
            carrier.ContentInBytes[x] = BitOperations.WriteBit(carrier.ContentInBytes[x],0,dataInBits[i]);

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

        for(var _byte: carrier.ContentInBytes)
            WriteBit(BitOperations.ReadKBit(_byte,0));

        return Stream.toByteArray();
    }


}
