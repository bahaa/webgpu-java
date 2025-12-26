package io.github.bahaa.webgpu.api;


public interface ComputePassEncoder extends ObjectBase {

    void setPipeline(final ComputePipeline pipeline);

    void setBindGroup(final int groupIndex, final BindGroup bindGroup);

    void dispatchWorkgroups(final int workgroupCountX, final int workgroupCountY, final int workgroupCountZ);

    void end();
}
