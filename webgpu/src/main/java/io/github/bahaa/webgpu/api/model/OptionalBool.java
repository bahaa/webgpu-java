package io.github.bahaa.webgpu.api.model;

public enum OptionalBool {
    FALSE(0x00000000),
    TRUE(0x00000001),
    UNDEFINED(0x00000002),
    FORCE32(0x7FFFFFFF);

    private final int value;

    OptionalBool(final int value) {
        this.value = value;
    }

    public static OptionalBool fromBoolean(final boolean bool) {
        return bool ? TRUE : FALSE;
    }

    public int value() {
        return this.value;
    }
}
