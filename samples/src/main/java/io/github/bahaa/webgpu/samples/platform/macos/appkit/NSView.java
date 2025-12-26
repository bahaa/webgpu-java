package io.github.bahaa.webgpu.samples.platform.macos.appkit;

import io.github.bahaa.webgpu.samples.platform.macos.ca.CAMetalLayer;
import io.github.bahaa.webgpu.samples.platform.macos.foundation.NSString;
import io.github.bahaa.webgpu.samples.platform.macos.runtime.Runtime;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public class NSView extends NSResponder {

    protected NSView(final MemorySegment id) {
        super(id);
    }

    public static NSView from(final MemorySegment id) {
        return new NSView(id);
    }

    public void toolTip(final NSString toolTip) {
        Runtime.msgSend(id(), Meta.SET_TOOL_TIP, toolTip.id());
    }

    public void setWantsLayer(final boolean wantsLayer) {
        Runtime.msgSend(id(), Meta.SET_WANTS_LAYER, wantsLayer ? 1 : 0);
    }

    public void setLayer(final CAMetalLayer layer) {
        Runtime.msgSend(id(), Meta.SET_LAYER, layer.id());
    }

    protected enum Meta {
        ;
        private static final MemorySegment SET_TOOL_TIP, SET_WANTS_LAYER, SET_LAYER;

        static {
            try (final var arena = Arena.ofConfined()) {
                SET_TOOL_TIP = Runtime.registerName(arena, "setToolTip:");
                SET_WANTS_LAYER = Runtime.registerName(arena, "setWantsLayer:");
                SET_LAYER = Runtime.registerName(arena, "setLayer:");
            }
        }
    }
}
