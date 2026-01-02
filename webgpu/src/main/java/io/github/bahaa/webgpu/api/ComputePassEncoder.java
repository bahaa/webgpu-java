package io.github.bahaa.webgpu.api;


public interface ComputePassEncoder extends PassEncoder {

    void setPipeline(final ComputePipeline pipeline);

    void dispatchWorkgroups(final int workgroupCountX, final int workgroupCountY, final int workgroupCountZ);

    void dispatchWorkgroupsIndirect(Buffer indirectBuffer, long indirectOffset);
}
