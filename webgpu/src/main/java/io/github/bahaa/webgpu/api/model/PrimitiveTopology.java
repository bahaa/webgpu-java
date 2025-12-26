package io.github.bahaa.webgpu.api.model;

public enum PrimitiveTopology {
    /**
     * 0x00000000.
     * Indicates no value is passed for this argument.
     */
    UNDEFINED(0x00000000),

    /**
     * Each vertex is a single point.
     */
    POINT_LIST(0x00000001),

    /**
     * Every two vertices form a line segment.
     */
    LINE_LIST(0x00000002),

    /**
     * Vertices form a connected series of line segments.
     */
    LINE_STRIP(0x00000003),

    /**
     * Every three vertices form a triangle.
     */
    TRIANGLE_LIST(0x00000004),

    /**
     * Every vertex after the first two forms a triangle with the previous two.
     */
    TRIANGLE_STRIP(0x00000005),

    FORCE32(0x7FFFFFFF);

    private final int value;

    PrimitiveTopology(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
