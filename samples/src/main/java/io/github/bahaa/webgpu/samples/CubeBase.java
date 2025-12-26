package io.github.bahaa.webgpu.samples;

import io.github.bahaa.webgpu.api.*;
import io.github.bahaa.webgpu.api.model.*;
import io.github.bahaa.webgpu.samples.math.Matrix4x4F;
import io.github.bahaa.webgpu.samples.math.Vec3F;

import java.lang.foreign.MemorySegment;
import java.util.EnumSet;
import java.util.List;

import static io.github.bahaa.webgpu.samples.meshes.Cube.*;

public abstract class CubeBase extends SampleBase {

    protected final Matrix4x4F projectionMatrix = Matrix4x4F.perspective((float) (2 * Math.PI) / 5f, 1, 1, 100.0f);
    protected RenderPipeline renderPipeline;
    protected Buffer uniformBuffer;
    protected BindGroup uniformBindGroup;
    protected Buffer verticesBuffer;
    protected Texture depthTexture;

    protected Matrix4x4F matrix() {
        final var now = System.currentTimeMillis() / 1500.0;

        final var matrix = Matrix4x4F.identity();
        matrix.translate(0, 0, -4);
        matrix.rotate(new Vec3F((float) Math.sin(now), (float) Math.cos(now), 0), 1);

        return this.projectionMatrix.multiply(matrix);
    }

    @Override
    protected void setup(final Device device, final Queue queue, final SurfaceCapabilities capabilities) {
        this.verticesBuffer = device.createBuffer(
                BufferDescriptor.builder().label("Vertices")
                        .usage(EnumSet.of(BufferUsage.VERTEX, BufferUsage.COPY_DST))
                        .size((long) CUBE_VERTICES.length * Float.BYTES)
                        .mappedAtCreation(true)
                        .build()
        );

        this.verticesBuffer.getMappedRange().copyFrom(MemorySegment.ofArray(CUBE_VERTICES));
        this.verticesBuffer.unmap();

        final var shaderModule = device.createShaderModule(ShaderModuleDescriptor.builder()
                .label("Shader")
                .source(ShaderSource.wgsl()
                        .code(loadFromClassPath(shaderFileName()))
                        .build())
                .build());

        this.renderPipeline = device.createRenderPipeline(RenderPipelineDescriptor.builder()
                .label("RenderPipeline")
                .vertex(VertexState.builder()
                        .module(shaderModule)
                        .entryPoint("vs_main")
                        .addBuffer(builder -> builder
                                .arrayStride(CUBE_VERTEX_SIZE)
                                .attributes(List.of(
                                        // position
                                        VertexAttribute.builder()
                                                .shaderLocation(0)
                                                .offset(CUBE_POSITION_OFFSET)
                                                .format(VertexFormat.FLOAT32X4)
                                                .build(),
                                        // uv
                                        VertexAttribute.builder()
                                                .shaderLocation(1)
                                                .offset(CUBE_UV_OFFSET)
                                                .format(VertexFormat.FLOAT32X2)
                                                .build()
                                ))
                                .build()
                        )
                        .build())
                .fragment(FragmentState.builder()
                        .module(shaderModule)
                        .entryPoint("fs_main")
                        .addTarget(ColorTargetState.builder()
                                .format(capabilities.getFormats().getFirst())
                                .writeMask(EnumSet.of(ColorWriteMask.ALL))
                                .build())
                        .build())
                .primitive(PrimitiveState.builder()
                        .topology(PrimitiveTopology.TRIANGLE_LIST)
                        // Backface culling since the cube is solid piece of geometry.
                        // Faces pointing away from the camera will be occluded by faces
                        // pointing toward the camera.
                        .cullMode(CullMode.BACK)
                        .build())
                // Enable depth testing so that the fragment closest to the camera
                // is rendered in front.
                .depthStencil(DepthStencilState.builder()
                        .depthWriteEnabled(OptionalBool.TRUE)
                        .depthCompare(CompareFunction.LESS)
                        .format(TextureFormat.DEPTH24_PLUS)
                        .build())
                .multisample(MultisampleState.builder()
                        .count(1)
                        .mask(0xFFFFFFFF)
                        .build())
                .build());

        this.depthTexture = device.createTexture(TextureDescriptor.builder()
                .size(Extent3D.builder()
                        .width(800)
                        .height(800)
                        .build())
                .format(TextureFormat.DEPTH24_PLUS)
                .usage(EnumSet.of(TextureUsage.RENDER_ATTACHMENT))
                .build());

        final var uniformBufferSize = 4 * 16; // 4x4 matrix
        this.uniformBuffer = device.createBuffer(BufferDescriptor.builder()
                .size(uniformBufferSize)
                .usage(EnumSet.of(BufferUsage.UNIFORM, BufferUsage.COPY_DST))
                .build());

        this.uniformBindGroup = createBindGroup(device);
    }

    @Override
    protected void render(final Device device, final Queue queue, final Surface surface, final Texture texture) {
        final var targetView = texture.createView(null);

        final var encoder = device.createCommandEncoder(CommandEncoderDescriptor.builder()
                .build());

        final var pass = encoder.beginRenderPass(RenderPassDescriptor.builder()
                .label("Clear BG")
                .addColorAttachment(RenderPassColorAttachment.builder()
                        .view(targetView)
                        .loadOp(LoadOp.CLEAR)
                        .storeOp(StoreOp.STORE)
                        .clearValue(Color.rgba(0.5, 0.5, 0.5, 1.0))
                        .build())
                .depthStencilAttachment(RenderPassDepthStencilAttachment.builder()
                        .view(this.depthTexture.createView(TextureViewDescriptor.builder()
                                .mipLevelCount(1)
                                .arrayLayerCount(1)
                                .build()))
                        .depthClearValue(1)
                        .depthLoadOp(LoadOp.CLEAR)
                        .depthStoreOp(StoreOp.STORE)
                        .build())
                .build());

        queue.writeBuffer(this.uniformBuffer, 0, matrix().data());

        pass.setPipeline(this.renderPipeline);
        pass.setBindGroup(0, this.uniformBindGroup);
        pass.setVertexBuffer(0, this.verticesBuffer);

        pass.draw(CUBE_VERTEX_COUNT);

        pass.end();
        pass.close();

        final var command = encoder.finish(CommandBufferDescriptor.builder().build());

        queue.submit(List.of(command));
        surface.present();
    }

    protected abstract String shaderFileName();

    protected abstract BindGroup createBindGroup(final Device device);
}
