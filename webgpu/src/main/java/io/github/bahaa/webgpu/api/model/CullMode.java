package io.github.bahaa.webgpu.api.model;

public enum CullMode {
    /**
     * 0x00000000.
     * Indicates no value is passed for this argument.
     */
    UNDEFINED(0x00000000),

    /**
     * No faces are discarded. Both sides of the triangle are rendered.
     */
    NONE(0x00000001),

    /**
     * The front-facing triangles are discarded.
     */
    FRONT(0x00000002),

    /**
     * The back-facing triangles are discarded (most common for 3D meshes).
     */
    BACK(0x00000003),

    FORCE32(0x7FFFFFFF);

    private final int value;

    CullMode(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

}
