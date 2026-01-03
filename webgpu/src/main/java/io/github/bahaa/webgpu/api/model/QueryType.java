package io.github.bahaa.webgpu.api.model;

public enum QueryType {
    OCCLUSION(0X00000001),
    TIMESTAMP(0X00000002),
    FORCE32(0X7FFFFFFF);

    private final int value;

    QueryType(final int value) {
        this.value = value;
    }

    public static QueryType valueOf(final int value) {
        for (final var type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("No constant with value " + value + " found");
    }

    public int value() {
        return this.value;
    }
}
