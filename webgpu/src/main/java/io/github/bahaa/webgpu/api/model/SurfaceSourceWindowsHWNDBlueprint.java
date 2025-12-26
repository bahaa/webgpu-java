package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUChainedStruct;
import io.github.bahaa.webgpu.ffm.WGPUSurfaceSourceWindowsHWND;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static java.lang.foreign.MemorySegment.NULL;

@Prototype.Blueprint
interface SurfaceSourceWindowsHWNDBlueprint extends SurfaceSource {

    MemorySegment hinstance();

    MemorySegment hwnd();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUSurfaceSourceWindowsHWND.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUSurfaceSourceWindowsHWND.hinstance(struct, hinstance());
        WGPUSurfaceSourceWindowsHWND.hwnd(struct, hwnd());

        final var chain = WGPUSurfaceSourceWindowsHWND.chain(struct);

        WGPUChainedStruct.next(chain, NULL);
        WGPUChainedStruct.sType(chain, SType.SURFACE_SOURCE_WINDOWS_HWND.value());
    }
}
