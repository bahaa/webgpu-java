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
     * The surface can present the frame, but may need reconfiguration.
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
     * The connection to the surface owner was lost.
     */
    LOST(0x00000005),

    /**
     * The system ran out of memory.
     */
    OUT_OF_MEMORY(0x00000006),

    /**
     * The WGPUDevice configured on the WGPUSurface was lost.
     */
    DEVICE_LOST(0x00000007),

    /**
     * The surface is not configured, or there was an error.
     */
    ERROR(0x00000008),

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
        return ERROR;
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
