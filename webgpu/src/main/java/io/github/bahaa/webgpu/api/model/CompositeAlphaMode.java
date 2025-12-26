package io.github.bahaa.webgpu.api.model;

/**
 * Describes how frames are composited with other contents on the screen.
 */
public enum CompositeAlphaMode {
    /**
     * Lets the WebGPU implementation choose the best mode (supported, and with the best performance)
     * between Opaque or Inherit.
     */
    AUTO(0x00000000),

    /**
     * The alpha component of the image is ignored and treated as if it is always 1.0.
     */
    OPAQUE(0x00000001),

    /**
     * The alpha component is respected and non-alpha components are assumed to be
     * already multiplied with the alpha component.
     */
    PREMULTIPLIED(0x00000002),

    /**
     * The alpha component is respected and non-alpha components are assumed to
     * NOT be already multiplied with the alpha component.
     */
    UNPREMULTIPLIED(0x00000003),

    /**
     * The handling of the alpha component is unknown to WebGPU and should be
     * handled by the application using system-specific APIs.
     */
    INHERIT(0x00000004),

    FORCE32(0x7FFFFFFF);

    private final int value;

    CompositeAlphaMode(final int value) {
        this.value = value;
    }

    public static CompositeAlphaMode fromValue(final int value) {
        for (final var mode : values()) {
            if (mode.value == value) {
                return mode;
            }
        }
        throw new IllegalArgumentException("Unknown CompositeAlphaMode: " + value);
    }

    public int value() {
        return this.value;
    }
}
