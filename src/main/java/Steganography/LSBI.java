package Steganography;

import Bmp.BmpFile;

public class LSBI implements Algorithm{
    @Override
    public BmpFile EmbedInformation(BmpFile carrier, byte[] data) {
        return carrier;

    }

    @Override
    public byte[] ExtractInformation(BmpFile carries) {
        return new byte[0];
    }
}
