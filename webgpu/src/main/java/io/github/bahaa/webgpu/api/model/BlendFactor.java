package io.github.bahaa.webgpu.api.model;

public enum BlendFactor {
    /**
     * 0x00000000. Indicates no value is passed for this argument.
     */
    UNDEFINED(0x00000000),

    /**
     * Factor is (0, 0, 0, 0)
     */
    ZERO(0x00000001),

    /**
     * Factor is (1, 1, 1, 1)
     */
    ONE(0x00000002),

    /**
     * Factor is the source color (S)
     */
    SRC(0x00000003),

    /**
     * Factor is (1-S, 1-S, 1-S, 1-S)
     */
    ONE_MINUS_SRC(0x00000004),

    /**
     * Factor is the source alpha (Sa)
     */
    SRC_ALPHA(0x00000005),

    /**
     * Factor is (1-Sa, 1-Sa, 1-Sa, 1-Sa)
     */
    ONE_MINUS_SRC_ALPHA(0x00000006),

    /**
     * Factor is the destination color (D)
     */
    DST(0x00000007),

    /**
     * Factor is (1-D, 1-D, 1-D, 1-D)
     */
    ONE_MINUS_DST(0x00000008),

    /**
     * Factor is the destination alpha (Da)
     */
    DST_ALPHA(0x00000009),

    /**
     * Factor is (1-Da, 1-Da, 1-Da, 1-Da)
     */
    ONE_MINUS_DST_ALPHA(0x0000000A),

    /**
     * Factor is (min(Sa, 1-Da), min(Sa, 1-Da), min(Sa, 1-Da), 1)
     */
    SRC_ALPHA_SATURATED(0x0000000B),

    /**
     * Factor is a constant value set via the render pass
     */
    CONSTANT(0x0000000C),

    /**
     * Factor is (1-Constant, 1-Constant, ...)
     */
    ONE_MINUS_CONSTANT(0x0000000D),

    /**
     * Dual-source blending: secondary source color
     */
    SRC1(0x0000000E),

    /**
     * Dual-source blending: 1 minus secondary source color
     */
    ONE_MINUS_SRC1(0x0000000F),

    /**
     * Dual-source blending: secondary source alpha
     */
    SRC1_ALPHA(0x00000010),

    /**
     * Dual-source blending: 1 minus secondary source alpha
     */
    ONE_MINUS_SRC1_ALPHA(0x00000011),

    FORCE32(0x7FFFFFFF);

    private final int value;

    BlendFactor(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }


}
