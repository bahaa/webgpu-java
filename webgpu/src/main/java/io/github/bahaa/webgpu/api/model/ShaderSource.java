package io.github.bahaa.webgpu.api.model;

public interface ShaderSource extends StructBlueprint {

    static ShaderSourceWGSL.Builder wgsl() {
        return ShaderSourceWGSL.builder();
    }
}
