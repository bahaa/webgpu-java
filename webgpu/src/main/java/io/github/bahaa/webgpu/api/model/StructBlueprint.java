package io.github.bahaa.webgpu.api.model;

import java.lang.foreign.Arena;
import java.lang.foreign.GroupLayout;
import java.lang.foreign.MemorySegment;
import java.util.List;

interface StructBlueprint {

    static <T extends StructBlueprint> MemorySegment structArray(final Arena arena,
                                                                 final GroupLayout layout,
                                                                 final List<T> structs) {
        final var array = arena.allocate(layout, structs.size());

        for (var i = 0; i < structs.size(); i++) {
            final var segment = array.asSlice(i * layout.byteSize(), layout.byteSize());
            structs.get(i).updateSegment(arena, segment);
        }

        return MemorySegment.ofAddress(array.address());
    }

    MemorySegment toSegment(Arena arena);

    void updateSegment(final Arena arena, final MemorySegment struct);

    default MemorySegment toSegmentAddress(final Arena arena) {
        return MemorySegment.ofAddress(toSegment(arena).address());
    }
}
