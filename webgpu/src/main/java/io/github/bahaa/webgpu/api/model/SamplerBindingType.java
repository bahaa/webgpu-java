package io.github.bahaa.webgpu.api.model;

public enum SamplerBindingType {
    /**
     * 0x00000000.
     * Indicates that this WGPUSamplerBindingLayout member of
     * its parent WGPUBindGroupLayoutEntry is not used.
     */
    BINDING_NOT_USED(0x00000000),

    /**
     * 0x00000001.
     * Indicates no value is passed for this argument.
     */
    UNDEFINED(0x00000001),

    FILTERING(0x00000002),

    NON_FILTERING(0x00000003),

    COMPARISON(0x00000004),

    /**
     * 0x7FFFFFFF.
     */
    FORCE32(0x7FFFFFFF);

    private final int value;

    SamplerBindingType(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
