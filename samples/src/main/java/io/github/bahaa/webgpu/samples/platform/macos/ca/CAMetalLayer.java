package io.github.bahaa.webgpu.samples.platform.macos.ca;

import io.github.bahaa.webgpu.samples.platform.macos.foundation.NSString;
import io.github.bahaa.webgpu.samples.platform.macos.runtime.NSObject;
import io.github.bahaa.webgpu.samples.platform.macos.runtime.Runtime;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public class CAMetalLayer extends NSObject {

    protected CAMetalLayer(final MemorySegment id) {
        super(id);
    }

    public static CAMetalLayer from(final MemorySegment id) {
        return new CAMetalLayer(id);
    }

    public static CAMetalLayer layer() {
        final var layer = Runtime.msgSend(Meta.CLASS, Meta.LAYER);
        return from(layer);
    }

    public NSString name() {
        final var name = Runtime.msgSend(id(), Meta.NAME);
        if (name.address() == 0) {
            return null;
        }
        return NSString.from(name);
    }

    protected enum Meta {
        ;
        public static final MemorySegment CLASS;

        public static final MemorySegment LAYER, NAME;

        static {
            try (final var arena = Arena.ofConfined()) {
                CLASS = Runtime.getClass(arena, "CAMetalLayer");

                LAYER = Runtime.registerName(arena, "layer");
                NAME = Runtime.registerName(arena, "name");
            }
        }
    }
}
