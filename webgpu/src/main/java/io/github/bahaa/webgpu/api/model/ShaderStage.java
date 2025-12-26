package io.github.bahaa.webgpu.api.model;

public enum ShaderStage {
    NONE(0X0000000000000000),
    VERTEX(0X0000000000000001),
    FRAGMENT(0X0000000000000002),
    COMPUTE(0X0000000000000004),
    ;
    private final long value;

    ShaderStage(final long value) {
        this.value = value;
    }

    public long value() {
        return this.value;
    }
}
