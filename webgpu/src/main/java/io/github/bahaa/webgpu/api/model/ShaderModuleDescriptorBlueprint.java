package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUShaderModuleDescriptor;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

@Prototype.Blueprint
interface ShaderModuleDescriptorBlueprint extends StructBlueprint {

    Optional<String> label();

    ShaderSource source();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUShaderModuleDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        label().ifPresent(label -> WGPUShaderModuleDescriptor.label(struct, StringView.from(label).toSegment(arena)));

        WGPUShaderModuleDescriptor.nextInChain(struct, source().toSegmentAddress(arena));
    }
}
