package Steganography;

import Bmp.BmpFile;

public interface Algorithm {

    public BmpFile EmbedInformation(BmpFile carrier, byte[] data) throws Exception;
    public byte[] ExtractInformation(BmpFile carries);
}
