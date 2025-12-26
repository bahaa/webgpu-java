package io.github.bahaa.webgpu.api.model;

/**
 * Describes the dimensionality of a texture view.
 */
public enum TextureViewDimension {
    /**
     * Indicates no value is passed for this argument.
     */
    UNDEFINED(0x00000000),

    /**
     * 1D texture view.
     */
    DIMENSION_1D(0x00000001),

    /**
     * 2D texture view.
     */
    DIMENSION_2D(0x00000002),

    /**
     * 2D array texture view.
     */
    D2_ARRAY(0x00000003),

    /**
     * Cube map texture view.
     */
    CUBE(0x00000004),

    /**
     * Cube map array texture view.
     */
    CUBE_ARRAY(0x00000005),

    /**
     * 3D texture view.
     */
    DIMENSION_3D(0x00000006),

    FORCE32(0x7FFFFFFF);

    private final int value;

    TextureViewDimension(final int value) {
        this.value = value;
    }

    /**
     * Maps an integer value to its corresponding enum constant.
     */
    public static TextureViewDimension fromValue(final int value) {
        for (final var dim : values()) {
            if (dim.value == value) {
                return dim;
            }
        }
        return UNDEFINED;
    }

    public int value() {
        return this.value;
    }
}
