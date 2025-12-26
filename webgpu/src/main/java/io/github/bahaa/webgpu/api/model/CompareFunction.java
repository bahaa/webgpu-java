package io.github.bahaa.webgpu.api.model;

public enum CompareFunction {
    /**
     * 0x00000000. Indicates no value is passed for this argument.
     */
    UNDEFINED(0x00000000),

    /**
     * Comparison always fails.
     */
    NEVER(0x00000001),

    /**
     * Passes if the new value is less than the existing value.
     */
    LESS(0x00000002),

    /**
     * Passes if the new value is equal to the existing value.
     */
    EQUAL(0x00000003),

    /**
     * Passes if the new value is less than or equal to the existing value.
     */
    LESS_EQUAL(0x00000004),

    /**
     * Passes if the new value is greater than the existing value.
     */
    GREATER(0x00000005),

    /**
     * Passes if the new value is not equal to the existing value.
     */
    NOT_EQUAL(0x00000006),

    /**
     * Passes if the new value is greater than or equal to the existing value.
     */
    GREATER_EQUAL(0x00000007),

    /**
     * Comparison always passes.
     */
    ALWAYS(0x00000008),

    FORCE32(0x7FFFFFFF);

    private final int value;

    CompareFunction(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
