package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUSurfaceDescriptor;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

@Prototype.Blueprint
interface SurfaceDescriptorBlueprint extends SurfaceSource {

    Optional<String> label();

    SurfaceSource source();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var source = this.source().toSegment(arena);

        final var descriptor = WGPUSurfaceDescriptor.allocate(arena);
        WGPUSurfaceDescriptor.nextInChain(descriptor, source);

        label().ifPresent(label -> WGPUSurfaceDescriptor.label(source, StringView.from(label).toSegment(arena)));

        return descriptor;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        throw new UnsupportedOperationException();
    }
}
