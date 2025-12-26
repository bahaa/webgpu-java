package io.github.bahaa.webgpu.api.model;

/**
 * Describes the operation to perform on a texture attachment at the beginning of a render pass.
 */
public enum LoadOp {

    /**
     * Indicates no value is passed for this argument.
     */
    UNDEFINED(0x00000000),

    /**
     * Loads the existing contents of the texture from memory.
     * Use this if you are drawing on top of what was already there.
     */
    LOAD(0x00000001),

    /**
     * Clears the texture to a specific value (the "clearValue") before any drawing commands.
     * This is usually more performant than LOAD if you don't need the previous data.
     */
    CLEAR(0x00000002),

    FORCE32(0x7FFFFFFF);

    private final int value;

    LoadOp(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
