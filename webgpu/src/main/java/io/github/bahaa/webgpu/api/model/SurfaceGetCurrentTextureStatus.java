package io.github.bahaa.webgpu.api.model;

/**
 * The status enum for wgpuSurfaceGetCurrentTexture.
 */
public enum SurfaceGetCurrentTextureStatus {
    /**
     * Everything is good and we can render this frame.
     */
    SUCCESS_OPTIMAL(0x00000001),

    /**
     * Still OK - the surface can present the frame, but in a suboptimal way. The surface may need reconfiguration.
     */
    SUCCESS_SUBOPTIMAL(0x00000002),

    /**
     * Some operation timed out while trying to acquire the frame.
     */
    TIMEOUT(0x00000003),

    /**
     * The surface is too different to be used compared to when it was created.
     */
    OUTDATED(0x00000004),

    /**
     * The connection to whatever owns the surface was lost, or generally needs to be fully reinitialized.
     */
    LOST(0x00000005),

    /**
     * There was some deterministic error (for example, the surface is not configured, or there was an
     *
     * @ref OutStructChainError). Should produce @ref ImplementationDefinedLogging containing details.
     */
    ERROR(0x00000006),

    /**
     * The surface texture was not acquired because the window is occluded
     * (e.g. minimized or fully covered by another window).
     * <p>
     * No texture is returned and the @c texture field of
     * {@code WGPUSurfaceTexture} will be NULL. The surface and swapchain remain
     * valid -- there is no need to reconfigure or recreate the surface.
     * <p>
     * Applications should skip rendering for the current frame and try
     * again once the window is no longer occluded. If you are using a
     * windowing library such as winit, listen for the window's "occluded"
     * event and request a new redraw when the window becomes visible again.
     * <p>
     * When does this occur?
     * <p>
     * Currently, this status is only produced by the Metal backend on macOS.
     * When a window is not visible (checked via the @c NSWindow
     * <p>
     * {@code occlusionState} property), acquiring the next drawable would block
     * for up to one second waiting for vsync. wgpu-native returns
     * {@code Occluded} instead to avoid that hang.
     * <p>
     * Other backends (Vulkan, DX12, GL) do not currently report this
     * status; an occluded window on those backends may produce
     * {@code WGPUSurfaceGetCurrentTextureStatus_Timeout} or simply succeed
     * normally.
     */
    OCCLUDED(0x00030001),

    FORCE32(0x7FFFFFFF);

    private final int value;

    SurfaceGetCurrentTextureStatus(final int value) {
        this.value = value;
    }

    /**
     * Maps an integer value to its corresponding enum constant.
     */
    public static SurfaceGetCurrentTextureStatus fromValue(final int value) {
        for (final var status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("invalid value %d".formatted(value));
    }

    public int getValue() {
        return this.value;
    }

    /**
     * Returns true if the status is one of the success states.
     */
    public boolean isSuccess() {
        return this == SUCCESS_OPTIMAL || this == SUCCESS_SUBOPTIMAL;
    }
}
