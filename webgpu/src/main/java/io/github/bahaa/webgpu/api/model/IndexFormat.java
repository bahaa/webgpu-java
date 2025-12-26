package io.github.bahaa.webgpu.api.model;

public enum IndexFormat {
    /**
     * 0x00000000.
     * Indicates no value is passed for this argument.
     */
    UNDEFINED(0x00000000),

    /**
     * 16-bit unsigned integer (2 bytes per index).
     */
    UINT16(0x00000001),

    /**
     * 32-bit unsigned integer (4 bytes per index).
     */
    UINT32(0x00000002),

    FORCE32(0x7FFFFFFF);

    private final int value;

    IndexFormat(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
