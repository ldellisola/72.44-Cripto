package Steganography;

import org.apache.commons.io.FilenameUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Information {
    public final String Extension;
    public final int Size;
    public final byte[] Data;

    private Information(String extension, int size, byte[] data) {
        Extension = extension;
        Size = size;
        Data = data;
    }

    public byte[] ToByteArray() throws IOException {
        var stream = new ByteArrayOutputStream();

        stream.write(ByteBuffer.allocate(4).putInt(Size).array());
        stream.write(Data);
        stream.write(Extension.getBytes());
        stream.write(0);

        return stream.toByteArray();
    }


    public static Information Load(File file) throws IOException {
        int size = (int) Files.size(file.toPath());
        String ext = "." + FilenameUtils.getExtension(file.getName());
        byte[] data = Files.readAllBytes(file.toPath());

        return new Information(ext, size, data);
    }

    public static Information Load(byte[] data) throws IOException {
        return Information.Load(new ByteArrayInputStream(data));
    }

    public static Information Load(ByteArrayInputStream stream) throws IOException {
        int size = ByteBuffer.wrap(stream.readNBytes(4)).getInt();
        var data = stream.readNBytes(size);
        String ext = StandardCharsets.US_ASCII.decode(ByteBuffer.wrap(stream.readNBytes(4))).toString();

        return new Information(ext, size, data);
    }


}
