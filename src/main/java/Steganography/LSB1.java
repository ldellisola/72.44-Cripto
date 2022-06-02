package Steganography;

import Bmp.BmpFile;

import java.io.ByteArrayOutputStream;

public class LSB1 implements Algorithm {
    @Override
    public BmpFile EmbedInformation(BmpFile carrier, byte[] data) throws Exception {

        if (data.length * 8 > carrier.Content.length * carrier.Header.BytesPerPixel())
            throw new Exception("Muy grande");

        var dataInBits = new boolean[data.length * 8];
        for (int i = 0; i < data.length; i++) {
            System.arraycopy(BitOperations.ReadLastKBits(data[i], 8), 0, dataInBits, i * 8, 8);
        }

        int i = 0;
        for (var pixel : carrier.Content) {
            if (i >= dataInBits.length)
                break;
            pixel.Blue = BitOperations.WriteBit(pixel.Blue, 0, dataInBits[i++]);
            if (i >= dataInBits.length)
                break;
            pixel.Green = BitOperations.WriteBit(pixel.Green, 0, dataInBits[i++]);
            if (i >= dataInBits.length)
                break;
            pixel.Red = BitOperations.WriteBit(pixel.Red, 0, dataInBits[i++]);
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

    @Override
    public byte[] ExtractInformation(BmpFile carrier) {

        for (var pixel : carrier.Content) {
            WriteBit(BitOperations.ReadKBit(pixel.Blue, 0));
            WriteBit(BitOperations.ReadKBit(pixel.Green, 0));
            WriteBit(BitOperations.ReadKBit(pixel.Red, 0));
        }

        return Stream.toByteArray();
    }


}
