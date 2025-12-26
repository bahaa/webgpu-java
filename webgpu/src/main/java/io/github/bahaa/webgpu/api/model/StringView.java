package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUStringView;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

class StringView implements StructBlueprint {

    private final String value;

    private StringView(final String value) {
        this.value = value == null ? "" : value;
    }

    public static StringView from(final String value) {
        return new StringView(value);
    }

    @Override
    public MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUStringView.allocate(arena);
        this.updateSegment(arena, struct);
        return struct;
    }

    @Override
    public void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUStringView.length(struct, this.value.length());
        WGPUStringView.data(struct, arena.allocateFrom(this.value));
    }
}
