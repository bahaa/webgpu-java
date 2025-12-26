package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUColorTargetState;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.EnumSet;
import java.util.Optional;

@Prototype.Blueprint
interface ColorTargetStateBlueprint extends StructBlueprint {
    TextureFormat format();

    Optional<BlendState> blend();

    @Option.DefaultCode("java.util.EnumSet.of(ColorWriteMask.ALL)")
    EnumSet<ColorWriteMask> writeMask();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUColorTargetState.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUColorTargetState.format(struct, this.format().value());
        blend().ifPresent(blend -> WGPUColorTargetState.blend(struct, blend.toSegment(arena)));

        WGPUColorTargetState.writeMask(struct, this.writeMask().stream()
                .mapToLong(ColorWriteMask::value)
                .reduce(0, (a, b) -> a | b));
    }
}
