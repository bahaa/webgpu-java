package io.github.bahaa.webgpu.api.model;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

public enum TextureUsage {
    NONE(0x0000000000000000),
    COPY_SRC(0x0000000000000001),
    COPY_DST(0x0000000000000002),
    TEXTURE_BINDING(0x0000000000000004),
    STORAGE_BINDING(0x0000000000000008),
    RENDER_ATTACHMENT(0x0000000000000010),
    TRANSIENT_ATTACHMENT(0x0000000000000020),
    ;

    private final long value;

    TextureUsage(final long value) {
        this.value = value;
    }

    public static EnumSet<TextureUsage> fromMask(final long mask) {
        return Arrays.stream(values())
                .filter(it -> (it.value() & mask) != 0)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(TextureUsage.class)));
    }

    public long value() {
        return this.value;
    }
}
