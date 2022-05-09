package Bmp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Arrays;

public class BmpFile {

    public final BmpHeader Header;
    public final BmpPixel[] Content;

    public BmpFile(String filename) throws Exception {
        var file = new File(filename);
        var fileContent = new ByteArrayInputStream(Files.readAllBytes(file.toPath()));

        Header = new BmpHeader(fileContent.readNBytes(54));
        Content = new BmpPixel[Header.RawImageSize / Header.BytesPerPixel()];

        for (int i = 0; i < Content.length; i++)
            Content[i] = new BmpPixel(fileContent.readNBytes(Header.BytesPerPixel()));

    }

    public BmpFile(File file) throws IOException, InvalidBmpException {
        var fileContent = new ByteArrayInputStream(Files.readAllBytes(file.toPath()));

        Header = new BmpHeader(fileContent.readNBytes(54));
        Content = new BmpPixel[Header.RawImageSize / Header.BytesPerPixel()];

        for (int i = 0; i < Content.length; i++)
            Content[i] = new BmpPixel(fileContent.readNBytes(Header.BytesPerPixel()));
    }



    public void Save(String filename) throws IOException {
        var file = new File(filename);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        stream.write(Header.ToByteArray());

        for (BmpPixel bmpPixel : Content) {
            stream.write(bmpPixel.ToByteArray());
        }

        Files.write(file.toPath(),stream.toByteArray());
    }
}
