package io.github.bahaa.webgpu.api.model;

/**
 * Describes the operation to perform on a texture attachment at the end of a render pass.
 */
public enum StoreOp {
    /**
     * Indicates no value is passed for this argument.
     */
    UNDEFINED(0x00000000),

    /**
     * The results of the render pass are written to the texture and preserved.
     * Use this for the final image you want to see or use later.
     */
    STORE(0x00000001),

    /**
     * The results of the render pass are discarded.
     * Highly performant for transient data like depth buffers that aren't needed after the pass.
     */
    DISCARD(0x00000002),

    FORCE32(0x7FFFFFFF);

    private final int value;

    StoreOp(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
