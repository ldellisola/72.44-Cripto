package Steganography;

import Bmp.BmpFile;

public interface Algorithm {

    BmpFile EmbedInformation(BmpFile carrier, byte[] data) throws Exception;

    byte[] ExtractInformation(BmpFile carries);
}
