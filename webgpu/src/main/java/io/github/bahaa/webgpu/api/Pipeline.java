package io.github.bahaa.webgpu.api;

public interface Pipeline extends ObjectBase {

    BindGroupLayout getBindGroupLayout(final int groupIndex);
}
