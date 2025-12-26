package io.github.bahaa.webgpu.samples;

import io.github.bahaa.webgpu.api.BindGroup;
import io.github.bahaa.webgpu.api.Device;
import io.github.bahaa.webgpu.api.model.BindGroupDescriptor;
import io.github.bahaa.webgpu.api.model.BindGroupEntry;

import java.util.List;

public class RotatingCube extends CubeBase {

    static void main(final String[] args) {
        new RotatingCube().run(args);
    }

    @Override
    protected String shaderFileName() {
        return "wgsl/rotating-cube.wgsl";
    }

    @Override
    protected BindGroup createBindGroup(final Device device) {
        return device.createBindGroup(BindGroupDescriptor.builder()
                .layout(this.renderPipeline.getBindGroupLayout(0))
                .entries(List.of(
                        BindGroupEntry.builder()
                                .binding(0)
                                .buffer(this.uniformBuffer)
                                .size(this.uniformBuffer.size())
                                .build()
                ))
                .build());
    }

    @Override
    protected String title() {
        return "WebGPU Rotating Cube";
    }
}
