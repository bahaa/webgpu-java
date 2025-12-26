package io.github.bahaa.webgpu.api.model;

/**
 * Describes when and in which order frames are presented on the screen.
 */
public enum PresentMode {
    /**
     * Present mode is not specified. Use the default.
     */
    UNDEFINED(0x00000000),

    /**
     * Waits for the next vertical blanking period (FIFO).
     * No tearing; limited to refresh rate. Always available.
     */
    FIFO(0x00000001),

    /**
     * Waits for vertical blanking but may skip if late.
     * Possible tearing; no full-frame stutter.
     */
    FIFO_RELAXED(0x00000002),

    /**
     * Updated immediately without waiting for vertical blank.
     * Tearing visible; minimal latency.
     */
    IMMEDIATE(0x00000003),

    /**
     * Waits for vertical blank to update to the latest image.
     * No tearing; frame-loop not limited to refresh rate.
     */
    MAILBOX(0x00000004),

    FORCE32(0x7FFFFFFF);

    private final int value;

    PresentMode(final int value) {
        this.value = value;
    }

    public static PresentMode fromValue(final int value) {
        for (final var mode : values()) {
            if (mode.value == value) {
                return mode;
            }
        }
        return UNDEFINED;
    }

    public int value() {
        return this.value;
    }
}
