package io.github.bahaa.webgpu.api.model;

public enum TextureUsage {
    NONE(0X0000000000000000),
    COPY_SRC(0X0000000000000001),
    COPY_DST(0X0000000000000002),
    TEXTURE_BINDING(0X0000000000000004),
    STORAGE_BINDING(0X0000000000000008),
    RENDER_ATTACHMENT(0X0000000000000010),
    ;

    private final long value;

    TextureUsage(final long value) {
        this.value = value;
    }

    public long value() {
        return this.value;
    }
}
