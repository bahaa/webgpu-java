package io.github.bahaa.webgpu.samples.platform;

public class PlatformException extends RuntimeException {
    public PlatformException(final String message) {
        super(message);
    }

    public PlatformException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PlatformException(final Throwable cause) {
        super(cause);
    }
}
