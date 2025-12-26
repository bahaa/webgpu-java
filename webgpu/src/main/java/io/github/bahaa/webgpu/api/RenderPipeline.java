package io.github.bahaa.webgpu.api;


public interface RenderPipeline extends ObjectBase {

    BindGroupLayout getBindGroupLayout(final int groupIndex);
}
