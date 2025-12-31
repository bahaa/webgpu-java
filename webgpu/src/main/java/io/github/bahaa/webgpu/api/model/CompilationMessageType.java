package io.github.bahaa.webgpu.api.model;

public enum CompilationMessageType {
    ERROR(0X00000001),
    WARNING(0X00000002),
    INFO(0X00000003);
    private final int value;

    CompilationMessageType(final int value) {
        this.value = value;
    }

    public static CompilationMessageType fromValue(final int value) {
        for (final var type : values()) {
            if (type.value() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid compilation type value: " + value);
    }

    public int value() {
        return this.value;
    }
}
