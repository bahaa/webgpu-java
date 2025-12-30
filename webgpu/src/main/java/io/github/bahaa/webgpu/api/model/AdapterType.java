package io.github.bahaa.webgpu.api.model;

public enum AdapterType {
    DISCRETE_GPU(0X00000001),
    INTEGRATED_GPU(0X00000002),
    CPU(0X00000003),
    UNKNOWN(0X00000004),
    FORCE32(0X7FFFFFFF),
    ;
    private final int value;

    AdapterType(final int value) {
        this.value = value;
    }

    public static AdapterType fromValue(final int value) {
        for (final var adapterType : values()) {
            if (adapterType.value == value) {
                return adapterType;
            }
        }
        throw new IllegalArgumentException("No AdapterType with value %d found".formatted(value));
    }

    public int value() {
        return this.value;
    }
}
