package io.github.bahaa.webgpu.samples;

import io.github.bahaa.webgpu.api.*;
import io.github.bahaa.webgpu.api.model.*;
import io.github.bahaa.webgpu.samples.math.Matrix4x4F;
import io.github.bahaa.webgpu.samples.math.Vec3F;

import java.util.EnumSet;
import java.util.List;

public class Points extends SampleBase {

    private static final int NUMBER_OF_POINTS = 1000;
    private static final Matrix4x4F VIEW_MATRIX = Matrix4x4F.lookAt(
            new Vec3F(0, 0, 1.5f), // position
            new Vec3F(0, 0, 0),    // target
            new Vec3F(0, 1, 0)     // up
    );

    private RenderPipeline renderPipeline;
    private Buffer vertexBuffer;
    private BindGroup bindGroup;
    private float[] uniformValues;
    private Buffer uniformBuffer;

    static void main(final String[] args) {
        new Points().run(args);
    }

    private static float[] createFibonacciSphereVertices(final int numSamples, final float radius) {
        final var vertices = new float[numSamples * 3];

        final var increment = Math.PI * (3 - Math.sqrt(5));
        final var offset = 2.0f / numSamples;

        for (var i = 0; i < numSamples; i++) {
            final var y = ((i * offset) - 1.0f) + (offset / 2.0f);
            final var r = (float) Math.sqrt(1.0 - Math.pow(y, 2));
            final var phi = i * increment;

            final var x = (float) (Math.cos(phi) * r);
            final var z = (float) (Math.sin(phi) * r);

            // Pack into the flat array
            final var index = i * 3;
            vertices[index] = x * radius;
            vertices[index + 1] = y * radius;
            vertices[index + 2] = z * radius;
        }

        return vertices;
    }

    @Override
    protected void setup(final Device device, final Queue queue) {
        final var module = loadShader(device, "wgsl/points.wgsl");

        this.renderPipeline = device.createRenderPipeline(RenderPipelineDescriptor.builder()
                .label("3D Points")
                .vertex(builder -> builder
                        .module(module)
                        .entryPoint("vs")
                        .addBuffer(VertexBufferLayout.builder()
                                .arrayStride(3 * 4) // 3 floats, 4 bytes each
                                .stepMode(VertexStepMode.INSTANCE)
                                .addAttribute(VertexAttribute.builder()
                                        .shaderLocation(0)
                                        .offset(0)
                                        .format(VertexFormat.FLOAT32X3)
                                        .build())
                                .build()
                        )
                )
                .fragment(builder -> builder
                        .module(module)
                        .entryPoint("fs")
                        .addTarget(t -> t
                                .format(getPreferredFormat())
                                .writeMask(EnumSet.of(ColorWriteMask.ALL))
                        )
                )
                .build());

        final var vertexData = createFibonacciSphereVertices(NUMBER_OF_POINTS, 1.0f);
        this.vertexBuffer = device.createBuffer(BufferDescriptor.builder()
                .label("Vertex Buffer")
                .size((long) vertexData.length * Float.BYTES)
                .usage(EnumSet.of(BufferUsage.VERTEX, BufferUsage.COPY_DST))
                .build());
        queue.writeBuffer(this.vertexBuffer, 0, vertexData);

        this.uniformValues = new float[16 + 2 + 1 + 1];

        this.uniformBuffer = device.createBuffer(BufferDescriptor.builder()
                .size((long) this.uniformValues.length * Float.BYTES)
                .usage(EnumSet.of(BufferUsage.UNIFORM, BufferUsage.COPY_DST))
                .build());

        this.bindGroup = device.createBindGroup(BindGroupDescriptor.builder()
                .layout(this.renderPipeline.getBindGroupLayout(0))
                .addEntry(builder -> builder
                        .binding(0)
                        .buffer(this.uniformBuffer)
                        .size((long) this.uniformValues.length * Float.BYTES)
                )
                .build());
    }

    @Override
    protected void render(final Device device, final Queue queue, final Surface surface, final Texture texture) {
        final var targetView = texture.createView();

        final float width = texture.width();
        final float height = texture.height();

        final var now = System.currentTimeMillis() / 1500.0;
        final var fov = (float) (90f * Math.PI / 180f);
        final var aspect = currentAspectRatio();
        final var projection = Matrix4x4F.perspective(fov, aspect, 0.1f, 50);
        final var viewProjection = Matrix4x4F.multiply(projection, VIEW_MATRIX);

        viewProjection.rotate(new Vec3F((float) Math.sin(now), (float) Math.cos(now), 0), 1);

        viewProjection.copyTo(this.uniformValues, 0);
        this.uniformValues[16] = width;
        this.uniformValues[17] = height;
        this.uniformValues[18] = 10;

        queue.writeBuffer(this.uniformBuffer, 0, this.uniformValues);

        final var encoder = device.createCommandEncoder(CommandEncoderDescriptor.builder()
                .build());

        final var pass = encoder.beginRenderPass(RenderPassDescriptor.builder()
                .label("Clear BG")
                .addColorAttachment(builder -> builder
                        .view(targetView)
                        .loadOp(LoadOp.CLEAR)
                        .storeOp(StoreOp.STORE)
                        .clearValue(Color.rgba(0.1, 0.1, 0.1, 1)))
                .build());

        pass.setPipeline(this.renderPipeline);
        pass.setVertexBuffer(0, this.vertexBuffer);
        pass.setBindGroup(0, this.bindGroup);

        pass.draw(6, NUMBER_OF_POINTS, 0, 0);

        pass.end();
        queue.submit(List.of(encoder.finish()));
        surface.present();
    }

    @Override
    protected String title() {
        return "WebGPU Points";
    }
}
