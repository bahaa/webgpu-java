package io.github.bahaa.webgpu.api.model;

public enum FrontFace {
    /**
     * 0x00000000.
     * Indicates no value is passed for this argument.
     */
    UNDEFINED(0x00000000),

    /**
     * Counter-clockwise: Vertices ordered 0 -> 1 -> 2 in CCW are front-facing.
     */
    CCW(0x00000001),

    /**
     * Clockwise: Vertices ordered 0 -> 1 -> 2 in CW are front-facing.
     */
    CW(0x00000002),

    FORCE32(0x7FFFFFFF);

    private final int value;

    FrontFace(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

}
