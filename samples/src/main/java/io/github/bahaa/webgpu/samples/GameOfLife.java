package io.github.bahaa.webgpu.samples;

import io.github.bahaa.webgpu.api.*;
import io.github.bahaa.webgpu.api.model.*;

import java.time.Duration;
import java.util.EnumSet;
import java.util.List;

public class GameOfLife extends SampleBase {

    private static final int GRID_SIZE = 128;
    private static final int WORKGROUP_SIZE = 8;

    private final float[] vertices = {
            //   X,    Y,
            -0.8f, -0.8f, // Triangle 1 (Blue)
            0.8f, -0.8f,
            0.8f, 0.8f,

            -0.8f, -0.8f, // Triangle 2 (Red)
            0.8f, 0.8f,
            -0.8f, 0.8f,
    };

    private final float[] uniformArray = {GRID_SIZE, GRID_SIZE};

    private RenderPipeline renderPipeline;
    private ComputePipeline computePipeline;

    private Buffer vertexBuffer;
    private BindGroup bindGroupA;
    private BindGroup bindGroupB;

    private long nanos = 0;
    private long step = 0;

    static void main(final String... args) {
        new GameOfLife().run(args);
    }

    @Override
    protected void setup(final Device device, final Queue queue, final SurfaceCapabilities capabilities) {
        this.vertexBuffer = device.createBuffer(BufferDescriptor.builder()
                .label("Cell Vertices")
                .size(((long) this.vertices.length * Float.BYTES))
                .usage(EnumSet.of(BufferUsage.VERTEX, BufferUsage.COPY_DST))
                .build());

        queue.writeBuffer(this.vertexBuffer, 0, this.vertices);

        final var uniformBuffer = device.createBuffer(BufferDescriptor.builder()
                .label("Grid Uniforms")
                .size(((long) this.uniformArray.length * Float.BYTES))
                .usage(EnumSet.of(BufferUsage.UNIFORM, BufferUsage.COPY_DST))
                .build());

        queue.writeBuffer(uniformBuffer, 0, this.uniformArray);

        final var cellStateArray = new int[GRID_SIZE * GRID_SIZE];

        final var cellStateStorageA = device.createBuffer(BufferDescriptor.builder()
                .label("Cell State A")
                .size(((long) cellStateArray.length * Integer.BYTES))
                .usage(EnumSet.of(BufferUsage.STORAGE, BufferUsage.COPY_DST))
                .build());

        final var cellStateStorageB = device.createBuffer(BufferDescriptor.builder()
                .label("Cell State B")
                .size(((long) cellStateArray.length * Integer.BYTES))
                .usage(EnumSet.of(BufferUsage.STORAGE, BufferUsage.COPY_DST))
                .build());

        for (var i = 0; i < cellStateArray.length; i++) {
            cellStateArray[i] = Math.random() > 0.6 ? 1 : 0;
        }

        queue.writeBuffer(cellStateStorageA, 0, cellStateArray);

        final var renderShaderModule = device.createShaderModule(ShaderModuleDescriptor.builder()
                .label("Shader")
                .source(ShaderSource.wgsl()
                        .code(loadFromClassPath("wgsl/render-game-of-life.wgsl"))
                        .build())
                .build());

        final var computeShaderModule = device.createShaderModule(ShaderModuleDescriptor.builder()
                .label("Shader")
                .source(ShaderSource.wgsl()
                        .code(loadFromClassPath("wgsl/compute-game-of-life.wgsl")
                                .replace("${WORKGROUP_SIZE}", String.valueOf(WORKGROUP_SIZE)))
                        .build())
                .build());

        final var bindGroupLayout = device.createBindGroupLayout(BindGroupLayoutDescriptor.builder()
                .label("Cell Bind Group Layout")
                .addEntry(builder -> builder
                        .binding(0)
                        .visibility(EnumSet.of(ShaderStage.VERTEX, ShaderStage.FRAGMENT, ShaderStage.COMPUTE))
                        .buffer(BufferBindingLayout.builder()
                                .type(BufferBindingType.UNIFORM)
                                .build())
                )
                .addEntry(builder -> builder
                        .binding(1)
                        .visibility(EnumSet.of(ShaderStage.VERTEX, ShaderStage.COMPUTE))
                        .buffer(BufferBindingLayout.builder()
                                .type(BufferBindingType.READ_ONLY_STORAGE)
                                .build())
                )
                .addEntry(builder -> builder
                        .binding(2)
                        .visibility(EnumSet.of(ShaderStage.COMPUTE))
                        .buffer(BufferBindingLayout.builder()
                                .type(BufferBindingType.STORAGE)
                                .build())
                )
                .build());

        final var pipelineLayout = device.createPipelineLayout(PipelineLayoutDescriptor.builder()
                .label("Pipeline")
                .bindGroupLayouts(List.of(bindGroupLayout))
                .build());

        this.renderPipeline = device.createRenderPipeline(RenderPipelineDescriptor.builder()
                .label("RenderPipeline")
                .layout(pipelineLayout)
                .vertex(VertexState.builder()
                        .module(renderShaderModule)
                        .entryPoint("vertexMain")
                        .addBuffer(VertexBufferLayout.builder()
                                .arrayStride(8L)
                                .stepMode(VertexStepMode.VERTEX)
                                .attributes(List.of(
                                        VertexAttribute.builder()
                                                .format(VertexFormat.FLOAT32X2)
                                                .offset(0L)
                                                .shaderLocation(0)
                                                .build()
                                ))
                                .build())
                        .build())
                .fragment(FragmentState.builder()
                        .module(renderShaderModule)
                        .entryPoint("fragmentMain")
                        .addTarget(ColorTargetState.builder()
                                .format(capabilities.getFormats().getFirst())
                                .writeMask(EnumSet.of(ColorWriteMask.ALL))
                                .build()
                        )
                        .build())
                .multisample(MultisampleState.builder()
                        .count(1)
                        .mask(0xFFFFFFFF)
                        .build())
                .build());

        this.computePipeline = device.createComputePipeline(ComputePipelineDescriptor.builder()
                .label("Simulation pipeline")
                .layout(pipelineLayout)
                .compute(ProgrammableStageDescriptor.builder()
                        .module(computeShaderModule)
                        .entryPoint("computeMain")
                        .build())
                .build());

        this.bindGroupA = device.createBindGroup(BindGroupDescriptor.builder()
                .label("Cell renderer bind group A")
                .layout(bindGroupLayout)
                .addEntry(BindGroupEntry.builder()
                        .binding(0)
                        .buffer(uniformBuffer)
                        .size(((long) this.uniformArray.length * Float.BYTES))
                        .build())
                .addEntry(BindGroupEntry.builder()
                        .binding(1)
                        .buffer(cellStateStorageA)
                        .size(((long) cellStateArray.length * Integer.BYTES))
                        .build())
                .addEntry(BindGroupEntry.builder()
                        .binding(2)
                        .buffer(cellStateStorageB)
                        .size(((long) cellStateArray.length * Integer.BYTES))
                        .build())
                .build());

        this.bindGroupB = device.createBindGroup(BindGroupDescriptor.builder()
                .label("Cell renderer bind group B")
                .layout(bindGroupLayout)
                .addEntry(builder -> builder
                        .binding(0)
                        .buffer(uniformBuffer)
                        .size(((long) this.uniformArray.length * Float.BYTES))
                )
                .addEntry(builder -> builder
                        .binding(1)
                        .buffer(cellStateStorageB)
                        .size(((long) cellStateArray.length * Integer.BYTES))
                )
                .addEntry(builder -> builder
                        .binding(2)
                        .buffer(cellStateStorageA)
                        .size(((long) cellStateArray.length * Integer.BYTES))
                )
                .build());
    }

    @Override
    protected void render(final Device device, final Queue queue, final Surface surface, final Texture texture) {

        final var elapsed = Duration.ofNanos(System.nanoTime() - this.nanos);
        if (elapsed.toMillis() < 200) {
            return;
        }

        this.nanos = System.nanoTime();

        final var targetView = texture.createView(null);

        final var encoder = device.createCommandEncoder(CommandEncoderDescriptor.builder()
                .build());

        final var computePass = encoder.beginComputePass(ComputePassDescriptor.builder()
                .label("Compute pass")
                .build());

        computePass.setPipeline(this.computePipeline);
        computePass.setBindGroup(0, this.step % 2 == 0 ? this.bindGroupA : this.bindGroupB);

        final var workgroupCount = GRID_SIZE / WORKGROUP_SIZE;
        computePass.dispatchWorkgroups(workgroupCount, workgroupCount, 1);

        computePass.end();

        this.step++;

        final var renderPass = encoder.beginRenderPass(RenderPassDescriptor.builder()
                .label("Clear BG")
                .addColorAttachment(builder -> builder
                        .view(targetView)
                        .loadOp(LoadOp.CLEAR)
                        .storeOp(StoreOp.STORE)
                        .clearValue(Color.rgb(0.0, 0.0, 0.0)))
                .build());

        renderPass.setPipeline(this.renderPipeline);
        renderPass.setVertexBuffer(0, this.vertexBuffer, 0, this.vertexBuffer.size());
        renderPass.setBindGroup(0, this.step % 2 == 0 ? this.bindGroupA : this.bindGroupB);

        renderPass.draw(this.vertices.length / 2, GRID_SIZE * GRID_SIZE, 0, 0);
        renderPass.end();

        final var command = encoder.finish(CommandBufferDescriptor.builder().build());

        queue.submit(List.of(command));
        surface.present();
    }

    @Override
    protected String title() {
        return "Game of Life";
    }
}
