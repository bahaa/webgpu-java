import io.github.bahaa.webgpu.api.Instance;
import io.github.bahaa.webgpu.api.model.*;
import io.github.bahaa.webgpu.tools.Images;

import javax.imageio.ImageIO;

void main() {
    final var width = 1_024;
    final var height = 1_024;

    try (final var instance = Instance.create();
         final var adapter = instance.requestAdapter(RequestAdapterOptions.create()).join();
         final var device = adapter.requestDevice(DeviceDescriptor.create()).join()) {


        final var texture = device.createTexture(TextureDescriptor.builder()
                .size(Extent3D.builder()
                        .width(width)
                        .height(height)
                        .build())
                .format(TextureFormat.RGBA8_UNORM)
                .usage(EnumSet.of(TextureUsage.COPY_SRC, TextureUsage.RENDER_ATTACHMENT))
                .build());

        final var shaderModule = device.createShaderModule(ShaderModuleDescriptor.builder()
                .source(ShaderSource.wgsl()
                        .code("""
                                @vertex
                                 fn vs_main(@builtin(vertex_index) VertexIndex : u32) -> @builtin(position) vec4f {
                                   var pos = array<vec2f, 3>(
                                     vec2(0.0, 0.5),
                                     vec2(-0.5, -0.5),
                                     vec2(0.5, -0.5)
                                   );
                                
                                   return vec4f(pos[VertexIndex], 0.0, 1.0);
                                 }
                                
                                @fragment
                                fn fs_main() -> @location(0) vec4<f32> {
                                    return vec4<f32>(1.0, 0.0, 0.0, 1.0);
                                }
                                """)
                        .build())
                .build());

        final var renderPipeline = device.createRenderPipeline(RenderPipelineDescriptor.builder()
                .vertex(VertexState.builder()
                        .module(shaderModule)
                        .entryPoint("vs_main")
                        .build())
                .fragment(FragmentState.builder()
                        .module(shaderModule)
                        .entryPoint("fs_main")
                        .addTarget(builder -> builder
                                .format(TextureFormat.RGBA8_UNORM)
                                .writeMask(EnumSet.of(ColorWriteMask.ALL))
                        )
                        .build())
                .build());

        final var targetView = texture.createView();
        final var encoder = device.createCommandEncoder(CommandEncoderDescriptor.create());
        final var pass = encoder.beginRenderPass(RenderPassDescriptor.builder()
                .addColorAttachment(builder -> builder
                        .view(targetView)
                        .loadOp(LoadOp.CLEAR)
                        .storeOp(StoreOp.STORE)
                        .clearValue(Color.rgba(0, 0, 0, 1f))) // black
                .build());
        
        pass.setPipeline(renderPipeline);
        pass.draw(3, 1, 0, 0);
        pass.end();

        final var outputBuffer = device.createBuffer(BufferDescriptor.builder()
                .size(width * height * 4) // 4 bytes per pixel RGBA
                .usage(EnumSet.of(BufferUsage.COPY_DST, BufferUsage.MAP_READ))
                .mappedAtCreation(false)
                .build());

        encoder.copyTextureToBuffer(TexelCopyTextureInfo.builder()
                        .texture(texture)
                        .build(),
                TexelCopyBufferInfo.builder()
                        .buffer(outputBuffer)
                        .layout(builder -> builder
                                .rowsPerImage(height)
                                .bytesPerRow(width * 4)
                        )
                        .build(),
                Extent3D.builder()
                        .width(width)
                        .height(height)
                        .depthOrArrayLayers(1)
                        .build());

        device.queue().submit(List.of(encoder.finish(CommandBufferDescriptor.create())));

        outputBuffer.mapAsync(EnumSet.of(MapMode.READ), 0, width * height * 4).join();
        final var bytes = outputBuffer.getMappedRange(0, width * height * 4).asByteBuffer();
        final var image = Images.bufferedImageFromRGBA8Data(width, height, bytes);
        outputBuffer.unmap();

        try (final var os = Files.newOutputStream(Path.of("./target/triablge.png"))) {
            ImageIO.write(image, "png", os);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
