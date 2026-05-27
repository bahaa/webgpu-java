package io.github.bahaa.webgpu.api.model;

public enum InstanceFeatureName {
    TIMED_WAIT_ANY(0x00000001),
    SHADER_SOURCE_SPIRV(0x00000002),
    MULTIPLE_DEVICES_PER_ADAPTER(0x00000003);
    
    private final int value;


    InstanceFeatureName(final int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
