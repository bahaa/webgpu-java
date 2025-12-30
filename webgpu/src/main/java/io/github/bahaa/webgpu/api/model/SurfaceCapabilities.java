package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUSurfaceCapabilities;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SurfaceCapabilities {

    private final Set<TextureUsage> usages;
    private final List<TextureFormat> formats;
    private final List<PresentMode> presentModes;
    private final List<CompositeAlphaMode> alphaModes;

    private SurfaceCapabilities(final Set<TextureUsage> usages,
                                final List<TextureFormat> formats,
                                final List<PresentMode> presentModes,
                                final List<CompositeAlphaMode> alphaModes) {
        this.usages = usages;
        this.formats = formats;
        this.presentModes = presentModes;
        this.alphaModes = alphaModes;
    }

    public static SurfaceCapabilities from(final MemorySegment segment) {
        final var usagesBits = WGPUSurfaceCapabilities.usages(segment);

        final var formatCount = WGPUSurfaceCapabilities.formatCount(segment);
        final var formats = new ArrayList<TextureFormat>();

        for (int i = 0; i < formatCount; i++) {
            final var format = WGPUSurfaceCapabilities.formats(segment).getAtIndex(ValueLayout.JAVA_INT, i);
            formats.add(TextureFormat.fromValue(format));
        }


        final var presentModeCount = WGPUSurfaceCapabilities.presentModeCount(segment);
        final var presentModes = new ArrayList<PresentMode>();

        for (int i = 0; i < presentModeCount; i++) {
            final var presentMode = WGPUSurfaceCapabilities
                    .presentModes(segment).getAtIndex(ValueLayout.JAVA_INT, i);
            presentModes.add(PresentMode.fromValue(presentMode));
        }

        final var alphaModeCount = WGPUSurfaceCapabilities.alphaModeCount(segment);
        final var alphaModes = new ArrayList<CompositeAlphaMode>();

        for (int i = 0; i < alphaModeCount; i++) {
            final var alphaMode = WGPUSurfaceCapabilities
                    .alphaModes(segment).getAtIndex(ValueLayout.JAVA_INT, i);
            alphaModes.add(CompositeAlphaMode.fromValue(alphaMode));
        }

        return new SurfaceCapabilities(
                Collections.unmodifiableSet(TextureUsage.fromMask(usagesBits)),
                Collections.unmodifiableList(formats),
                Collections.unmodifiableList(presentModes),
                Collections.unmodifiableList(alphaModes)
        );
    }

    public Set<TextureUsage> getUsages() {
        return this.usages;
    }

    public List<TextureFormat> getFormats() {
        return this.formats;
    }

    public List<PresentMode> getPresentModes() {
        return this.presentModes;
    }

    public List<CompositeAlphaMode> getAlphaModes() {
        return this.alphaModes;
    }
}
