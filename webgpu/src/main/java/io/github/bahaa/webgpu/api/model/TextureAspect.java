package io.github.bahaa.webgpu.api.model;

/**
 * Specifies which aspect of a texture to select.
 */
public enum TextureAspect {
    /**
     * Indicates no value is passed for this argument.
     */
    UNDEFINED(0x00000000),

    /**
     * Selects all available aspects of the texture format.
     */
    ALL(0x00000001),

    /**
     * Selects only the stencil aspect of a depth-stencil texture.
     */
    STENCIL_ONLY(0x00000002),

    /**
     * Selects only the depth aspect of a depth-stencil texture.
     */
    DEPTH_ONLY(0x00000003),

    FORCE32(0x7FFFFFFF);

    private final int value;

    TextureAspect(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
