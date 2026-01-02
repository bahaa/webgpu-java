package io.github.bahaa.webgpu.api;

public interface PassEncoder extends ObjectBase {

    void end();

    void setBindGroup(final int groupIndex, final BindGroup bindGroup, final int[] dynamicOffsets);

    default void setBindGroup(final int groupIndex, final BindGroup bindGroup) {
        setBindGroup(groupIndex, bindGroup, null);
    }

    void insertDebugMarker(String markerLabel);

    void popDebugGroup();

    void pushDebugGroup(String groupLabel);
}
