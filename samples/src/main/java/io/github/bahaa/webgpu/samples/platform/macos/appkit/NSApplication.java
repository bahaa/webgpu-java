package io.github.bahaa.webgpu.samples.platform.macos.appkit;

import io.github.bahaa.webgpu.samples.platform.macos.foundation.NSArray;
import io.github.bahaa.webgpu.samples.platform.macos.runtime.NSObject;
import io.github.bahaa.webgpu.samples.platform.macos.runtime.Runtime;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static io.github.bahaa.webgpu.samples.platform.macos.runtime.Runtime.msgSend;

public class NSApplication extends NSObject {

    protected NSApplication(final MemorySegment id) {
        super(id);
    }

    public static NSApplication sharedApplication() {
        return new NSApplication(msgSend(Meta.CLASS, Meta.SHARED_APPLICATION));
    }

    public Optional<NSWindow> mainWindow() {
        final var mainWindow = msgSend(this.id, Meta.MAIN_WINDOW);

        if (mainWindow.address() == 0) {
            return Optional.empty();
        }

        return Optional.of(NSWindow.from(mainWindow));
    }

    public Optional<NSWindow> keyWindow() {
        final var mainWindow = msgSend(this.id, Meta.KEY_WINDOW);

        if (mainWindow.address() == 0) {
            return Optional.empty();
        }

        return Optional.of(NSWindow.from(mainWindow));
    }

    public List<NSWindow> windows() {
        final var windows = NSArray.from(msgSend(id(), Meta.WINDOWS));

        return IntStream.range(0, windows.count())
                .mapToObj(windows::objectAtIndex)
                .map(NSWindow::from)
                .toList();
    }

    protected enum Meta {
        ;
        public static final MemorySegment CLASS;

        public static final MemorySegment SHARED_APPLICATION,
                WINDOWS, MAIN_WINDOW, KEY_WINDOW;

        static {
            try (final var arena = Arena.ofConfined()) {
                CLASS = Runtime.getClass(arena, "NSApplication");

                WINDOWS = Runtime.getClass(arena, "windows");
                SHARED_APPLICATION = Runtime.registerName(arena, "sharedApplication");
                MAIN_WINDOW = Runtime.registerName(arena, "mainWindow");
                KEY_WINDOW = Runtime.registerName(arena, "keyWindow");
            }
        }
    }
}
