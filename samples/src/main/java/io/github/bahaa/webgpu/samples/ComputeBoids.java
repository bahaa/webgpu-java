package io.github.bahaa.webgpu.samples;

import io.github.bahaa.webgpu.api.*;
import io.github.bahaa.webgpu.api.model.*;

import java.lang.foreign.MemorySegment;
import java.util.List;

public class ComputeBoids extends SampleBase {

    private static final int NUM_PARTICLES = 1500;

    private final SimParams simParams = new SimParams();
    private final Buffer[] particleBuffers = new Buffer[2];
    private final BindGroup[] particleBindGroups = new BindGroup[2];
    private int time = 0;
    private Buffer simParamBuffer;
    private ComputePipeline computePipeline;
    private RenderPipeline renderPipeline;
    private Buffer spriteVertexBuffer;

    static void main(final String... args) {
        new ComputeBoids().run(args);
    }

    @Override
    protected void setup(final Device device, final Queue queue) {
        final var renderShaderModule = loadShader(device, "wgsl/compute-boids-render.wgsl");

        this.renderPipeline = device.createRenderPipeline(RenderPipelineDescriptor.builder()
                .vertex(builder -> builder
                        .module(renderShaderModule)
                        .entryPoint("vert_main")
                        // instanced particles buffer
                        .addBuffer(VertexBufferLayout.builder()
                                .arrayStride(4 * 4)
                                .stepMode(VertexStepMode.INSTANCE)
                                .addAttribute(VertexAttribute.builder()
                                        .shaderLocation(0)
                                        .offset(0)
                                        .format(VertexFormat.FLOAT32X2)
                                        .build())
                                .addAttribute(VertexAttribute.builder()
                                        .shaderLocation(1)
                                        .offset(2 * 4)
                                        .format(VertexFormat.FLOAT32X2)
                                        .build())
                                .build())
                        // vertex buffer
                        .addBuffer(VertexBufferLayout.builder()
                                .arrayStride(2 * 4)
                                .stepMode(VertexStepMode.VERTEX)
                                .addAttribute(VertexAttribute.builder()
                                        .shaderLocation(2)
                                        .offset(0)
                                        .format(VertexFormat.FLOAT32X2)
                                        .build())
                                .build())
                )
                .fragment(builder -> builder
                        .module(renderShaderModule)
                        .entryPoint("frag_main")
                        .addTarget(ColorTargetState.builder()
                                .format(getPreferredFormat())
                                .build())
                )
                .primitive(builder -> builder
                        .topology(PrimitiveTopology.TRIANGLE_LIST))
                .build());

        this.computePipeline = device.createComputePipeline(ComputePipelineDescriptor.builder()
                .compute(builder -> builder
                        .module(loadShader(device, "wgsl/compute-boids-compute.wgsl"))
                        .entryPoint("main")
                )
                .build());

        final var vertexBufferData = new float[]{
                -0.01f, -0.02f, 0.01f,
                -0.02f, 0.0f, 0.02f,
        };

        this.spriteVertexBuffer = device.createBuffer(BufferDescriptor.builder()
                .size((long) vertexBufferData.length * Float.BYTES)
                .addUsage(BufferUsage.VERTEX)
                .mappedAtCreation(true)
                .build());

        this.spriteVertexBuffer.getMappedRange().copyFrom(MemorySegment.ofArray(vertexBufferData));
        this.spriteVertexBuffer.unmap();

        this.simParamBuffer = device.createBuffer(BufferDescriptor.builder()
                .size(SimParams.BYTE_SIZE)
                .addUsage(BufferUsage.UNIFORM)
                .addUsage(BufferUsage.COPY_DST)
                .build());
        updateSimParams(queue);

        final var initialParticleData = new float[NUM_PARTICLES * 4];
        for (var i = 0; i < NUM_PARTICLES; ++i) {
            initialParticleData[4 * i] = 2 * (float) (Math.random() - 0.5);
            initialParticleData[4 * i + 1] = 2 * (float) (Math.random() - 0.5);
            initialParticleData[4 * i + 2] = 2 * (float) (Math.random() - 0.5) * 0.1f;
            initialParticleData[4 * i + 3] = 2 * (float) (Math.random() - 0.5) * 0.1f;
        }

        for (var i = 0; i < 2; i++) {
            this.particleBuffers[i] = device.createBuffer(BufferDescriptor.builder()
                    .size((long) initialParticleData.length * Float.BYTES)
                    .addUsage(BufferUsage.VERTEX)
                    .addUsage(BufferUsage.STORAGE)
                    .mappedAtCreation(true)
                    .build());
            this.particleBuffers[i].getMappedRange().copyFrom(MemorySegment.ofArray(initialParticleData));
            this.particleBuffers[i].unmap();
        }

        for (var i = 0; i < 2; ++i) {
            final var index = i;
            this.particleBindGroups[i] = device.createBindGroup(BindGroupDescriptor.builder()
                    .layout(this.computePipeline.getBindGroupLayout(0))
                    .addEntry(builder -> builder
                            .binding(0)
                            .buffer(this.simParamBuffer)
                            .size(this.simParamBuffer.size())
                    )
                    .addEntry(builder -> builder
                            .binding(1)
                            .buffer(this.particleBuffers[index])
                            .offset(0)
                            .size((long) initialParticleData.length * Float.BYTES)
                    )
                    .addEntry(builder -> builder
                            .binding(2)
                            .buffer(this.particleBuffers[(index + 1) % 2])
                            .offset(0)
                            .size((long) initialParticleData.length * Float.BYTES)
                    )
                    .build());
        }
    }

    @Override
    protected void render(final Device device, final Queue queue, final Surface surface, final Texture texture) {
        final var targetView = texture.createView();

        final var encoder = device.createCommandEncoder(CommandEncoderDescriptor.builder()
                .build());

        final var computePass = encoder.beginComputePass(ComputePassDescriptor.create());
        computePass.setPipeline(this.computePipeline);
        computePass.setBindGroup(0, this.particleBindGroups[this.time % 2]);
        computePass.dispatchWorkgroups(Math.ceilDiv(NUM_PARTICLES, 64));
        computePass.end();

        final var renderPass = encoder.beginRenderPass(RenderPassDescriptor.builder()
                .addColorAttachment(builder -> builder
                        .view(targetView)
                        .loadOp(LoadOp.CLEAR)
                        .storeOp(StoreOp.STORE)
                        .clearValue(Color.rgba(0, 0, 0, 1)))
                .build());
        renderPass.setPipeline(this.renderPipeline);
        renderPass.setVertexBuffer(0, this.particleBuffers[(this.time + 1) % 2]);
        renderPass.setVertexBuffer(1, this.spriteVertexBuffer);
        renderPass.draw(3, NUM_PARTICLES, 0, 0);
        renderPass.end();

        queue.submit(List.of(encoder.finish()));
        surface.present();

        this.time++;
    }

    @Override
    protected String title() {
        return "WebGPU Compute Boids";
    }

    private void updateSimParams(final Queue queue) {
        queue.writeBuffer(this.simParamBuffer, 0, new float[]{
                this.simParams.deltaT,
                this.simParams.rule1Distance,
                this.simParams.rule2Distance,
                this.simParams.rule3Distance,
                this.simParams.rule1Scale,
                this.simParams.rule2Scale,
                this.simParams.rule3Scale,
        });
    }

    private static class SimParams {
        public static final long BYTE_SIZE = 7 * Float.BYTES;

        float deltaT = 0.04f;
        float rule1Distance = 0.1f;
        float rule2Distance = 0.025f;
        float rule3Distance = 0.025f;
        float rule1Scale = 0.02f;
        float rule2Scale = 0.05f;
        float rule3Scale = 0.005f;
    }
}
