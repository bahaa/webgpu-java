package io.github.bahaa.webgpu.api;


import io.github.bahaa.webgpu.api.model.TextureDimension;
import io.github.bahaa.webgpu.api.model.TextureFormat;
import io.github.bahaa.webgpu.api.model.TextureUsage;
import io.github.bahaa.webgpu.api.model.TextureViewDescriptor;
import org.jspecify.annotations.Nullable;

import java.util.EnumSet;

public interface Texture extends ObjectBase {

    TextureView createView(@Nullable final TextureViewDescriptor descriptor);

    default TextureView createView() {
        return createView(null);
    }

    int width();

    int height();

    int depthOrArrayLayers();

    int mipLevelCount();

    EnumSet<TextureUsage> usage();

    TextureDimension dimension();

    TextureFormat format();
}
