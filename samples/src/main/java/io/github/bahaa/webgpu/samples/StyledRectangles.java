package io.github.bahaa.webgpu.samples;

import io.github.bahaa.webgpu.api.*;
import io.github.bahaa.webgpu.api.model.*;
import io.github.bahaa.webgpu.samples.math.Vec2F;
import io.github.bahaa.webgpu.samples.math.Vec4F;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

/// This example is ported from: [here](https://tchayen.com/thousands-styled-rectangles-in-120fps-on-gpu)
public class StyledRectangles extends SampleBase {

    private final List<Rectangle> rectangles = new ArrayList<>();

    private RenderPipeline renderPipeline;
    private Buffer vertexBuffer;
    private BindGroup bindGroup;
    private Buffer rectangleBuffer;

    static void main(final String... args) {
        new StyledRectangles().run(args);
    }

    @Override
    protected void setup(final Device device, final Queue queue, final SurfaceCapabilities capabilities) {
        this.rectangles.add(new Rectangle(
                Color.rgba(1, 0.5, 1, 1),
                new Vec2F(50, 100),
                new Vec2F(100, 100),
                new Vec4F(10, 10, 10, 10),
                0.25f
        ));
        this.rectangles.add(new Rectangle(
                Color.rgba(1, 1, 0.5, 1),
                new Vec2F(450, 150),
                new Vec2F(100, 100),
                new Vec4F(0, 0, 0, 0),
                20
        ));
        this.rectangles.add(new Rectangle(
                Color.rgba(0.5, 0.25, 1, 1),
                new Vec2F(150, 300),
                new Vec2F(100, 100),
                new Vec4F(0, 10, 20, 30),
                0.25f
        ));
        this.rectangles.add(new Rectangle(
                Color.rgba(1, 0.5, 0.25, 1),
                new Vec2F(250, 50),
                new Vec2F(100, 100),
                new Vec4F(50, 50, 50, 50),
                0.25f
        ));

        this.rectangles.add(new Rectangle(
                Color.rgba(1, 0.5, 1, 1),
                new Vec2F(400, 400),
                new Vec2F(100, 100),
                new Vec4F(10, 10, 10, 10),
                20
        ));
        this.rectangles.add(new Rectangle(
                Color.rgba(0.5, 0.25, 0.5, 1),
                new Vec2F(400, 400),
                new Vec2F(100, 100),
                new Vec4F(10, 10, 10, 10),
                0.25f
        ));

        this.rectangles.add(new Rectangle(
                Color.rgba(1, 0.5, 1, 1),
                new Vec2F(401, 401),
                new Vec2F(98, 98),
                new Vec4F(9, 9, 9, 9),
                0.25f
        ));

        this.rectangles.add(new Rectangle(
                Color.rgba(0.5, 0.5, 0.9, 1),
                new Vec2F(461, 560),
                new Vec2F(98, 200),
                new Vec4F(9, 9, 9, 9),
                0.6f
        ));

        final var shaderModule = loadShader(device, "wgsl/styled-rectangles.wgsl");

        this.vertexBuffer = device.createBuffer(BufferDescriptor.builder()
                .size(2 * 2 * 3 * Float.BYTES)
                .usage(EnumSet.of(BufferUsage.VERTEX, BufferUsage.COPY_DST))
                .build());

        this.rectangleBuffer = device.createBuffer(BufferDescriptor.builder()
                .size((long) this.rectangles.size() * Rectangle.BYTE_SIZE * Float.BYTES)
                .usage(EnumSet.of(BufferUsage.STORAGE, BufferUsage.COPY_DST))
                .build());

        final var configBuffer = device.createBuffer(BufferDescriptor.builder()
                .size((long) this.rectangles.size() * 2 * Float.BYTES)
                .usage(EnumSet.of(BufferUsage.UNIFORM, BufferUsage.COPY_DST))
                .build());
        queue.writeBuffer(configBuffer, 0, new float[]{800f, 800f});

        final var bindGroupLayout = device.createBindGroupLayout(BindGroupLayoutDescriptor.builder()
                .addEntry(builder -> builder
                        .binding(0)
                        .visibility(EnumSet.of(ShaderStage.VERTEX, ShaderStage.FRAGMENT))
                        .buffer(BufferBindingLayout.builder()
                                .type(BufferBindingType.READ_ONLY_STORAGE)
                                .build())
                )
                .addEntry(builder -> builder
                        .binding(1)
                        .visibility(EnumSet.of(ShaderStage.VERTEX, ShaderStage.FRAGMENT))
                        .buffer(BufferBindingLayout.builder()
                                .type(BufferBindingType.UNIFORM)
                                .build())
                )
                .build());

        final var pipelineLayout = device.createPipelineLayout(PipelineLayoutDescriptor.builder()
                .addBindGroupLayout(bindGroupLayout)
                .build());

        this.bindGroup = device.createBindGroup(BindGroupDescriptor.builder()
                .layout(bindGroupLayout)
                .addEntry(builder -> builder
                        .binding(0)
                        .buffer(this.rectangleBuffer)
                        .size(this.rectangleBuffer.size())
                )
                .addEntry(builder -> builder
                        .binding(1)
                        .buffer(configBuffer)
                        .size(configBuffer.size())
                )
                .build());

        this.renderPipeline = device.createRenderPipeline(RenderPipelineDescriptor.builder()
                .layout(pipelineLayout)
                .vertex(builder -> builder
                        .module(shaderModule)
                        .entryPoint("vs_main")
                        .addBuffer(VertexBufferLayout.builder()
                                .arrayStride(2 * Float.BYTES)
                                .addAttribute(VertexAttribute.builder()
                                        .shaderLocation(0)
                                        .offset(0)
                                        .format(VertexFormat.FLOAT32X2)
                                        .build())
                                .build())
                )
                .fragment(builder -> builder
                        .module(shaderModule)
                        .entryPoint("fs_main")
                        .addTarget(ColorTargetState.builder()
                                .format(capabilities.getFormats().getFirst())
                                .blend(BlendState.builder()
                                        .color(BlendComponent.builder()
                                                .srcFactor(BlendFactor.SRC_ALPHA)
                                                .dstFactor(BlendFactor.ONE_MINUS_SRC_ALPHA)
                                                .build())
                                        .alpha(BlendComponent.builder()
                                                .srcFactor(BlendFactor.SRC_ALPHA)
                                                .dstFactor(BlendFactor.ONE_MINUS_SRC_ALPHA)
                                                .build())
                                        .build())
                                .build())
                )
                .multisample(builder -> builder
                        .count(4)
                )
                .build());

        // Just regular full-screen quad consisting of two triangles.
        final var vertices = new float[]{0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1};
        queue.writeBuffer(this.vertexBuffer, 0, vertices);
    }

    @Override
    protected void render(final Device device, final Queue queue, final Surface surface, final Texture texture) {
        final var multisampledTexture = device.createTexture(TextureDescriptor.builder()
                .sampleCount(4)
                .size(Extent3D.builder()
                        .width(texture.width())
                        .height(texture.height())
                        .build())
                .usage(EnumSet.of(TextureUsage.RENDER_ATTACHMENT))
                .format(texture.format())
                .build());
        final var targetView = texture.createView();
        final var encoder = device.createCommandEncoder(CommandEncoderDescriptor.create());
        final var pass = encoder.beginRenderPass(RenderPassDescriptor.builder()
                .label("Clear BG")
                .addColorAttachment(builder -> builder
                        .view(multisampledTexture.createView())
                        .resolveTarget(targetView)
                        .loadOp(LoadOp.CLEAR)
                        .storeOp(StoreOp.STORE)
                        .clearValue(Color.rgba(1, 1, 1, 1)))
                .build());

        pass.setPipeline(this.renderPipeline);
        pass.setVertexBuffer(0, this.vertexBuffer);
        pass.setBindGroup(0, this.bindGroup);
        pass.setViewport(0, 0,
                texture.width(),
                texture.height(),
                0, 1);

        final var rectangleArray = new float[this.rectangles.size() * Rectangle.BYTE_SIZE];
        for (int i = 0; i < this.rectangles.size(); i++) {
            this.rectangles.get(i).copyTo(rectangleArray, i * Rectangle.BYTE_SIZE, texture.width(), texture.height());
        }
        queue.writeBuffer(this.rectangleBuffer, 0, rectangleArray);

        pass.draw(6, this.rectangles.size(), 0, 0);

        pass.end();
        queue.submit(List.of(encoder.finish(CommandBufferDescriptor.create())));
        surface.present();
    }

    @Override
    protected String title() {
        return "WebGPU Styled Rectangles";
    }

    private record Rectangle(Color color, Vec2F position, Vec2F size, Vec4F corners, float sigma) {

        public static final int BYTE_SIZE = 16;

        public void copyTo(final float[] array, final int offset, final float width, final float height) {
            Objects.requireNonNull(array);

            array[offset] = this.color.red();
            array[offset + 1] = this.color.green();
            array[offset + 2] = this.color.blue();
            array[offset + 3] = this.color.alpha();
            array[offset + 4] = this.position.x();
            array[offset + 5] = this.position.y();
            array[offset + 6] = 0; // Padding
            array[offset + 7] = this.sigma;
            array[offset + 8] = this.corners.x();
            array[offset + 9] = this.corners.y();
            array[offset + 10] = this.corners.z();
            array[offset + 11] = this.corners.w();
            array[offset + 12] = this.size.x();
            array[offset + 13] = this.size.y();
            array[offset + 14] = width;
            array[offset + 15] = height;
        }
    }
}
