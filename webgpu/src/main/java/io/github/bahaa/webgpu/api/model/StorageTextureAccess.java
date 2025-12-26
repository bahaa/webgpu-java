package io.github.bahaa.webgpu.api.model;

public enum StorageTextureAccess {

    BINDING_NOT_USED(0X00000000),
    UNDEFINED(0X00000001),
    WRITE_ONLY(0X00000002),
    READ_ONLY(0X00000003),
    READ_WRITE(0X00000004),
    FORCE32(0X7FFFFFFF);

    private final int value;

    StorageTextureAccess(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

}
