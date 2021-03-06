package Bmp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class BmpFile {

    public final BmpHeader Header;
    private final BmpPixel[] Content;
    public final byte[] ContentInBytes;

    public BmpFile(String filename) throws Exception {
        var file = new File(filename);
        var fileContent = new ByteArrayInputStream(Files.readAllBytes(file.toPath()));

        Header = new BmpHeader(fileContent.readNBytes(54));
        Content = new BmpPixel[Header.RawImageSize / Header.BytesPerPixel()];

        for (int i = 0; i < Content.length; i++)
            Content[i] = new BmpPixel(fileContent.readNBytes(Header.BytesPerPixel()));

        ContentInBytes = new byte[Content.length * 3];
        LoadBits();

    }

    public BmpFile(File file) throws IOException, InvalidBmpException {
        var fileContent = new ByteArrayInputStream(Files.readAllBytes(file.toPath()));

        Header = new BmpHeader(fileContent.readNBytes(54));
        Content = new BmpPixel[Header.RawImageSize / Header.BytesPerPixel()];

        for (int i = 0; i < Content.length; i++)
            Content[i] = new BmpPixel(fileContent.readNBytes(Header.BytesPerPixel()));

        ContentInBytes = new byte[Content.length * 3];
        LoadBits();
    }

    private void LoadBits(){
        int i = 0;
        for (var pixel: Content) {
            if (i >= ContentInBytes.length)
                break;

            ContentInBytes[i++] = pixel.Blue;
            if (i >= ContentInBytes.length)
                break;

            ContentInBytes[i++] = pixel.Green;
            if (i >= ContentInBytes.length)
                break;

            ContentInBytes[i++] = pixel.Red;
            if (i >= ContentInBytes.length)
                break;
        }
    }


    public void Save(String filename) throws IOException {
        var file = new File(filename);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        stream.write(Header.ToByteArray());
        stream.write(ContentInBytes);


        Files.write(file.toPath(), stream.toByteArray());
    }
}
