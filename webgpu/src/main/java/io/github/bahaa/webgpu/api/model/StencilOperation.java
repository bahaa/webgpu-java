package io.github.bahaa.webgpu.api.model;

public enum StencilOperation {
    /**
     * 0x00000000.
     * Indicates no value is passed for this argument.
     */
    UNDEFINED(0x00000000),

    /**
     * Keep the current stencil value.
     */
    KEEP(0x00000001),

    /**
     * Set the stencil value to zero.
     */
    ZERO(0x00000002),

    /**
     * Replace the stencil value with the reference value.
     */
    REPLACE(0x00000003),

    /**
     * Bitwise invert the current stencil value.
     */
    INVERT(0x00000004),

    /**
     * Increment the current stencil value, clamping to the maximum representable value.
     */
    INCREMENT_CLAMP(0x00000005),

    /**
     * Decrement the current stencil value, clamping to zero.
     */
    DECREMENT_CLAMP(0x00000006),

    /**
     * Increment the current stencil value, wrapping to zero on overflow.
     */
    INCREMENT_WRAP(0x00000007),

    /**
     * Decrement the current stencil value, wrapping to the maximum value on underflow.
     */
    DECREMENT_WRAP(0x00000008),

    FORCE32(0x7FFFFFFF);

    private final int value;

    StencilOperation(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
