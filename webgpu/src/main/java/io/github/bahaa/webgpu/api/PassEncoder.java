package io.github.bahaa.webgpu.api;

public interface PassEncoder extends ObjectBase {

    void end();

    void setBindGroup(final int groupIndex, final BindGroup bindGroup);

    void insertDebugMarker(String markerLabel);

    void popDebugGroup();

    void pushDebugGroup(String groupLabel);
}
