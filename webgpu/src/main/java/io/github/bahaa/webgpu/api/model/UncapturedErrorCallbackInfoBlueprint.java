package io.github.bahaa.webgpu.api.model;

import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

@Prototype.Blueprint
interface UncapturedErrorCallbackInfoBlueprint extends StructBlueprint {

    @Override
    default MemorySegment toSegment(final Arena arena) {
        throw new UnsupportedOperationException();
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        throw new UnsupportedOperationException();
    }
}
