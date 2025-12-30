package io.github.bahaa.webgpu.api.model;

public enum TextureDimension {
    Undefined(0x00000000),
    DIMENSION_1D(0x00000001),
    DIMENSION_2D(0x00000002),
    DIMENSION_3D(0x00000003),
    Force32(0x7FFFFFFF);
    private final int value;

    TextureDimension(final int value) {
        this.value = value;
    }

    public static TextureDimension fromValue(final int value) {
        for (final var dimension : values()) {
            if (dimension.value == value) {
                return dimension;
            }
        }

        throw new IllegalArgumentException("Unknown TextureDimension value %d".formatted(value));
    }

    public int value() {
        return this.value;
    }
}
