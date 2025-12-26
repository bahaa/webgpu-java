package io.github.bahaa.webgpu.samples;

import io.github.bahaa.webgpu.api.BindGroup;
import io.github.bahaa.webgpu.api.Device;
import io.github.bahaa.webgpu.api.model.*;

import javax.imageio.ImageIO;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.EnumSet;

public class TexturedCube extends CubeBase {

    static void main(final String[] args) {
        new TexturedCube().run(args);
    }

    @Override
    protected String shaderFileName() {
        return "wgsl/textured-cube.wgsl";
    }

    @Override
    protected BindGroup createBindGroup(final Device device) {
        final var sampler = device.createSampler(SamplerDescriptor.builder()
                .magFilter(FilterMode.LINEAR)
                .minFilter(FilterMode.LINEAR)
                .build());

        try (final var is = TexturedCube.class.getClassLoader()
                .getResourceAsStream("images/hummingbird.png")) {
            assert is != null;

            final var image = ImageIO.read(is);

            final var cubeTexture = device.createTexture(TextureDescriptor.builder()
                    .size(Extent3D.builder()
                            .width(image.getWidth())
                            .height(image.getHeight())
                            .depthOrArrayLayers(1)
                            .build())
                    .format(TextureFormat.RGBA8_UNORM_SRGB)
                    .usage(EnumSet.of(
                            TextureUsage.TEXTURE_BINDING,
                            TextureUsage.COPY_DST,
                            TextureUsage.RENDER_ATTACHMENT
                    ))
                    .build());

            final var pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData().clone();

            // 0, 1, 2, 3
            // A, B, G, R
            // R, B, G, A
            // R, G, B, A
            for (var i = 0; i < pixels.length; i += 4) {
                final var alpha = pixels[i];
                pixels[i] = pixels[i + 3];
                pixels[i + 3] = alpha;

                final var green = pixels[i + 1];
                pixels[i + 1] = pixels[i + 2];
                pixels[i + 2] = green;
            }

            device.queue().writeTexture(TexelCopyTextureInfo.builder()
                            .texture(cubeTexture)
                            .build(),
                    pixels,
                    TexelCopyBufferLayout.builder()
                            .bytesPerRow(image.getWidth() * 4)
                            .rowsPerImage(image.getHeight())
                            .build(),
                    Extent3D.builder()
                            .width(image.getWidth())
                            .height(image.getHeight())
                            .depthOrArrayLayers(1)
                            .build());


            return device.createBindGroup(BindGroupDescriptor.builder()
                    .layout(this.renderPipeline.getBindGroupLayout(0))
                    .addEntry(builder -> builder
                            .binding(0)
                            .buffer(this.uniformBuffer)
                            .size(this.uniformBuffer.size())
                    )
                    .addEntry(builder -> builder
                            .binding(1)
                            .sampler(sampler)
                    )
                    .addEntry(builder -> builder
                            .binding(2)
                            .textureView(cubeTexture.createView(TextureViewDescriptor.builder()
                                    .arrayLayerCount(1)
                                    .build())))
                    .build());
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    protected String title() {
        return "WebGPU Rotating Textured Cube";
    }
}
