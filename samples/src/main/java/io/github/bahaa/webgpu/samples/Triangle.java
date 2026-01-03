package io.github.bahaa.webgpu.samples;

import io.github.bahaa.webgpu.api.*;
import io.github.bahaa.webgpu.api.model.*;

import java.util.EnumSet;
import java.util.List;

public class Triangle extends SampleBase {


    private final Color color = Color.rgba(0.0, 0.0, 0.0, 0.0);

    private RenderPipeline renderPipeline;

    static void main(final String[] args) {
        new Triangle().run(args);
    }

    @Override
    protected void setup(final Device device, final Queue queue) {

        final var shaderModule = device.createShaderModule(ShaderModuleDescriptor.builder()
                .label("Shader")
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

        final var pipelineLayout = device.createPipelineLayout(PipelineLayoutDescriptor.builder()
                .label("Pipeline")
                .build());

        this.renderPipeline = device.createRenderPipeline(RenderPipelineDescriptor.builder()
                .label("RenderPipeline")
                .layout(pipelineLayout)
                .vertex(VertexState.builder()
                        .module(shaderModule)
                        .entryPoint("vs_main")
                        .build())
                .fragment(FragmentState.builder()
                        .module(shaderModule)
                        .entryPoint("fs_main")
                        .addTarget(builder -> builder
                                .format(getPreferredFormat())
                                .writeMask(EnumSet.of(ColorWriteMask.ALL))
                        )
                        .build())
                .primitive(PrimitiveState.builder()
                        .topology(PrimitiveTopology.TRIANGLE_LIST)
                        .build())
                .multisample(MultisampleState.builder()
                        .count(1)
                        .mask(0xFFFFFFFF)
                        .build())
                .build());
    }

    @Override
    protected void render(final Device device, final Queue queue,
                          final Surface surface,
                          final Texture texture) {
        final var targetView = texture.createView();
        final var encoder = device.createCommandEncoder(CommandEncoderDescriptor.create());

        final var pass = encoder.beginRenderPass(RenderPassDescriptor.builder()
                .label("Clear BG")
                .addColorAttachment(builder -> builder
                        .view(targetView)
                        .loadOp(LoadOp.CLEAR)
                        .storeOp(StoreOp.STORE)
                        .clearValue(this.color))
                .build());
        pass.setPipeline(this.renderPipeline);
        pass.draw(3, 1);
        pass.end();

        queue.submit(List.of(encoder.finish()));
        surface.present();
    }

    @Override
    protected String title() {
        return "WebGPU Triangle!";
    }
}
