package io.github.bahaa.webgpu.samples.platform.macos.appkit;

import io.github.bahaa.webgpu.samples.platform.macos.runtime.NSObject;
import io.github.bahaa.webgpu.samples.platform.macos.runtime.Runtime;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public class NSToolbar extends NSObject {


    protected NSToolbar(final MemorySegment id) {
        super(id);
    }

    public static NSToolbar create() {
        final var id = alloc(Meta.CLASS);
        Runtime.msgSend(id, NSObject.Meta.INIT);
        return new NSToolbar(id);
    }

    protected enum Meta {
        ;
        public static final MemorySegment CLASS;

        static {
            try (final var arena = Arena.ofConfined()) {
                CLASS = Runtime.getClass(arena, "NSToolbar");
            }
        }
    }
}
