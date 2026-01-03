package io.github.bahaa.webgpu.api;


public interface ComputePassEncoder extends PassEncoder {

    void setPipeline(final ComputePipeline pipeline);

    void dispatchWorkgroups(final int workgroupCountX, final int workgroupCountY, final int workgroupCountZ);

    default void dispatchWorkgroups(final int workgroupCountX, final int workgroupCountY) {
        dispatchWorkgroups(workgroupCountX, workgroupCountY, 1);
    }

    default void dispatchWorkgroups(final int workgroupCountX) {
        dispatchWorkgroups(workgroupCountX, 1, 1);
    }

    void dispatchWorkgroupsIndirect(Buffer indirectBuffer, long indirectOffset);
}
