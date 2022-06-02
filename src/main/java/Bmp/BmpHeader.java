package Bmp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class BmpHeader {
    public enum BitmapId {BM, BA, CI, CP, IC, PT}

    public BitmapId Id;
    public int FileSize;
    public int BitmapDataOffset;
    public int HeaderSize;
    public int Width;
    public int Height;
    public short ColorPlanes;
    public short BitsPerPixel;
    public int CompressionMethod;
    public int RawImageSize;
    public int HorizontalResolution;
    public int VerticalResolution;
    public int ColorPalette;
    public int ImportantColors;

    public BmpHeader(byte[] content) throws InvalidBmpException {

        this.Id = BitmapId.valueOf(StandardCharsets.US_ASCII.decode(ByteBuffer.wrap(content, 0, 2)).toString());
        this.FileSize = GetInt(content, 2);
        this.BitmapDataOffset = GetInt(content, 10);
        this.HeaderSize = GetInt(content, 14);
        this.Width = GetInt(content, 18);
        this.Height = GetInt(content, 22);
        this.ColorPlanes = GetShort(content, 26);
        this.BitsPerPixel = GetShort(content, 28);
        this.CompressionMethod = GetInt(content, 30);
        this.RawImageSize = GetInt(content, 34);
        this.HorizontalResolution = GetInt(content, 38);
        this.VerticalResolution = GetInt(content, 42);
        this.ColorPalette = GetInt(content, 46);
        this.ImportantColors = GetInt(content, 50);

        Validate();
    }

    public void Validate() throws InvalidBmpException {
        if (this.Id != BitmapId.BM)
            throw new InvalidBmpException("The file ID is not BM");

        if (this.BitsPerPixel != 24)
            throw new InvalidBmpException("Only 24 bits per pixel are supported");

        if (this.CompressionMethod != 0)
            throw new InvalidBmpException("Compressed files are not supported");

    }

    public byte[] ToByteArray() {
        var array = new byte[54];

        CopyArray(Id.name().getBytes(), array, 0);
        CopyArray(GetByteArray(FileSize), array, 2);
        CopyArray(GetByteArray(BitmapDataOffset), array, 10);
        CopyArray(GetByteArray(HeaderSize), array, 14);
        CopyArray(GetByteArray(Width), array, 18);
        CopyArray(GetByteArray(Height), array, 22);
        CopyArray(GetByteArray(ColorPlanes), array, 26);
        CopyArray(GetByteArray(BitsPerPixel), array, 28);
        CopyArray(GetByteArray(CompressionMethod), array, 30);
        CopyArray(GetByteArray(RawImageSize), array, 34);
        CopyArray(GetByteArray(HorizontalResolution), array, 38);
        CopyArray(GetByteArray(VerticalResolution), array, 42);
        CopyArray(GetByteArray(ColorPalette), array, 46);
        CopyArray(GetByteArray(ImportantColors), array, 50);

        return array;
    }

    public int BytesPerPixel() {
        return BitsPerPixel / 8;
    }

    private void CopyArray(byte[] src, byte[] dest, int offset) {
        System.arraycopy(src, 0, dest, offset, src.length);
    }

    private byte[] GetByteArray(int data) {
        return ByteBuffer.allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(data)
                .array();
    }

    private byte[] GetByteArray(short data) {
        return ByteBuffer.allocate(2)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putShort(data)
                .array();
    }

    private int GetInt(byte[] data, int offset) {
        return ByteBuffer.wrap(data, offset, 4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .getInt();
    }

    private short GetShort(byte[] data, int offset) {
        return ByteBuffer.wrap(data, offset, 2)
                .order(ByteOrder.LITTLE_ENDIAN)
                .getShort();
    }

}
