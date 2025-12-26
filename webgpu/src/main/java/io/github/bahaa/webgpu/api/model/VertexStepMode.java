package io.github.bahaa.webgpu.api.model;

public enum VertexStepMode {
    /**
     * 0x00000000.
     * This WGPUVertexBufferLayout is a "hole" in the WGPUVertexState buffers array.
     */
    VERTEX_BUFFER_NOT_USED(0x00000000),

    /**
     * 0x00000001.
     * Indicates no value is passed for this argument.
     */
    UNDEFINED(0x00000001),

    VERTEX(0x00000002),

    INSTANCE(0x00000003),

    FORCE32(0x7FFFFFFF);

    private final int value;

    VertexStepMode(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
