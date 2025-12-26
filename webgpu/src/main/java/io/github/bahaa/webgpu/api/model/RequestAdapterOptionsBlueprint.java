package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.api.Surface;
import io.github.bahaa.webgpu.ffm.WGPURequestAdapterOptions;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

@Prototype.Blueprint
interface RequestAdapterOptionsBlueprint extends StructBlueprint {
    @Option.Default("CORE")
    FeatureLevel featureLevel();

    Optional<PowerPreference> powerPreference();

    boolean forceFallbackAdapter();

    Optional<BackendType> backendType();

    Optional<Surface> compatibleSurface();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPURequestAdapterOptions.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPURequestAdapterOptions.featureLevel(struct, this.featureLevel() == null ? 0 : this.featureLevel().value());

        powerPreference().ifPresent(powerPreference ->
                WGPURequestAdapterOptions.powerPreference(struct, powerPreference.value()));
        WGPURequestAdapterOptions.forceFallbackAdapter(struct, this.forceFallbackAdapter() ? 1 : 0);
        backendType().ifPresent(backendType ->
                WGPURequestAdapterOptions.backendType(struct, backendType.value()));
        compatibleSurface().ifPresent(compatibleSurface ->
                WGPURequestAdapterOptions.compatibleSurface(struct, compatibleSurface.pointer()));
    }
}
