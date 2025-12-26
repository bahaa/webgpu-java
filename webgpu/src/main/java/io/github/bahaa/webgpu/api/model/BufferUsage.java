package io.github.bahaa.webgpu.api.model;

public enum BufferUsage {
    NONE(0X0000000000000000),
    MAP_READ(0X0000000000000001),
    MAP_WRITE(0X0000000000000002),
    COPY_SRC(0X0000000000000004),
    COPY_DST(0X0000000000000008),
    INDEX(0X0000000000000010),
    VERTEX(0X0000000000000020),
    UNIFORM(0X0000000000000040),
    STORAGE(0X0000000000000080),
    INDIRECT(0X0000000000000100),
    QUERY_RESOLVE(0X0000000000000200),
    ;

    private final long value;

    BufferUsage(final long value) {
        this.value = value;
    }

    public long value() {
        return this.value;
    }
}
