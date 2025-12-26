package io.github.bahaa.webgpu.api;


import io.github.bahaa.webgpu.api.model.TextureViewDescriptor;

public interface Texture extends ObjectBase {

    TextureView createView(final TextureViewDescriptor descriptor);
}
