package io.github.bahaa.webgpu.tools;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.nio.ByteBuffer;
import java.util.Objects;

public enum Images {
    ;

    public static BufferedImage bufferedImageFromRGBA8Data(final int width, final int height,
                                                           final ByteBuffer rgbaBuffer) {
        Objects.requireNonNull(rgbaBuffer);
        if (rgbaBuffer.isDirect()) {
            final var rgbaData = new byte[rgbaBuffer.remaining()];
            rgbaBuffer.get(rgbaData);
            return bufferedImageFromRGBA8Data(width, height, rgbaData);
        } else {
            return bufferedImageFromRGBA8Data(width, height, rgbaBuffer.array());
        }
    }

    public static BufferedImage bufferedImageFromRGBA8Data(final int width, final int height,
                                                           final byte[] rgbaData) {
        final var buffer = new DataBufferByte(rgbaData, rgbaData.length);

        // (0=R, 1=G, 2=B, 3=A for RGBA)
        final var bandOffsets = new int[]{0, 1, 2, 3};

        final var raster = Raster.createInterleavedRaster(buffer,
                width,
                height,
                width * 4,  // scanline stride (4 bytes per pixel)
                4,                          // pixel stride (4 bytes per pixel)
                bandOffsets,
                null);

        final var cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        final int[] bits = {8, 8, 8, 8}; // 8 bits per component
        final var cm = new ComponentColorModel(
                cs,
                bits,
                true,
                false,
                Transparency.TRANSLUCENT,
                DataBuffer.TYPE_BYTE);

        return new BufferedImage(cm, raster, false, null);
    }
}
