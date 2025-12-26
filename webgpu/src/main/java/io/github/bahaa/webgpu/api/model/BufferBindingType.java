package io.github.bahaa.webgpu.api.model;

public enum BufferBindingType {
    /**
     * 0x00000000.
     * Indicates that this WGPUBufferBindingLayout member of
     * its parent WGPUBindGroupLayoutEntry is not used.
     */
    BINDING_NOT_USED(0x00000000),

    /**
     * 0x00000001.
     * Indicates no value is passed for this argument.
     */
    UNDEFINED(0x00000001),

    UNIFORM(0X00000002),

    STORAGE(0X00000003),

    READ_ONLY_STORAGE(0X00000004),

    /**
     * 0x7FFFFFFF.
     */
    FORCE32(0x7FFFFFFF);

    private final int value;

    BufferBindingType(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
