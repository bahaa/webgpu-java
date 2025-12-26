package io.github.bahaa.webgpu.api.model;

public enum BackendType {
    UNDEFINED(0X00000000),
    NULL(0X00000001),
    WEBGPU(0X00000002),
    D3D11(0X00000003),
    D3D12(0X00000004),
    METAL(0X00000005),
    VULKAN(0X00000006),
    OPENGL(0X00000007),
    OPENGL_ES(0X00000008),
    ;
    private final int value;

    BackendType(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
