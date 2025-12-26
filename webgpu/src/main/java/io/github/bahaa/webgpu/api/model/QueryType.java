package io.github.bahaa.webgpu.api.model;

public enum QueryType {
    OCCLUSION(0X00000001),
    TIMESTAMP(0X00000002),
    FORCE32(0X7FFFFFFF);

    private final int value;

    QueryType(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
