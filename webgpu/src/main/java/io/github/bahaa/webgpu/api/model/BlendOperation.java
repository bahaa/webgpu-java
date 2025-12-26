package io.github.bahaa.webgpu.api.model;

public enum BlendOperation {
    /**
     * 0x00000000. Indicates no value is passed for this argument.
     */
    UNDEFINED(0x00000000),

    /**
     * src + dst. The most common operation for transparency.
     */
    ADD(0x00000001),

    /**
     * src - dst.
     */
    SUBTRACT(0x00000002),

    /**
     * dst - src.
     */
    REVERSE_SUBTRACT(0x00000003),

    /**
     * min(src, dst). Results in the darker of the two colors.
     */
    MIN(0x00000004),

    /**
     * max(src, dst). Results in the lighter of the two colors.
     */
    MAX(0x00000005),

    FORCE32(0x7FFFFFFF);

    private final int value;

    BlendOperation(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
