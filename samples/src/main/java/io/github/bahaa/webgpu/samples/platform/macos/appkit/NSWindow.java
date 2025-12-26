package io.github.bahaa.webgpu.samples.platform.macos.appkit;

import io.github.bahaa.webgpu.samples.platform.macos.foundation.NSString;
import io.github.bahaa.webgpu.samples.platform.macos.runtime.NSObject;
import io.github.bahaa.webgpu.samples.platform.macos.runtime.Runtime;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.List;
import java.util.stream.LongStream;

import static io.github.bahaa.webgpu.samples.platform.macos.runtime.Runtime.msgSend;
import static io.github.bahaa.webgpu.samples.platform.macos.runtime.Runtime.registerName;

public class NSWindow extends NSObject {

    private final static int NS_WINDOW_STYLE_FULL_SIZE_CONTENT_VIEW = 1 << 15;

    private NSWindow(final MemorySegment nsWindow) {
        super(nsWindow);
    }

    public static NSWindow firstWindowTitled(final String title) {
        final var windows = allAppWindows();

        if (windows.isEmpty()) {
            return null;
        }

        return windows.stream().filter(it -> it.title().equals(title)).findFirst().orElse(null);
    }

    public static NSWindow firstWindow() {
        final var windows = allAppWindows();

        if (windows.isEmpty()) {
            return null;
        }

        return windows.stream().findFirst().orElse(null);
    }

    public static NSWindow from(final MemorySegment nsWindow) {
        return new NSWindow(nsWindow);
    }

    public static List<NSWindow> allAppWindows() {
        try (final var arena = Arena.ofConfined()) {
            final var nsApplication = Runtime.getClass(arena, "NSApplication");
            final var sharedApplicationSel = registerName(arena, "sharedApplication");
            final var sharedApplication = msgSend(nsApplication, sharedApplicationSel);

            final var windowsSel = registerName(arena, "windows");
            final var windows = msgSend(sharedApplication, windowsSel);

            final var objectAtIndexSel = registerName(arena, "objectAtIndex:");
            final var countSel = registerName(arena, "count");
            final var count = msgSend(windows, countSel).address();

            if (count <= 0) {
                return List.of();
            }

            return LongStream.range(0, count)
                    .mapToObj(it -> msgSend(windows, objectAtIndexSel, it))
                    .map(NSWindow::from)
                    .toList();
        }
    }

    public String title() {
        final var nsTitle = msgSend(this.id, Meta.TITLE);
        return NSString.from(nsTitle).toUtf8String();
    }

    public void setTitleBarAppearsTransparent(final boolean value) {
        msgSend(this.id, Meta.TITLEBAR_APPEARS_TRANSPARENT, (byte) (value ? 1 : 0));
    }

    public void fullSizeContentView() {
        final var styleMask = (int) msgSend(this.id, Meta.STYLE_MASK).address();
        msgSend(this.id, Meta.SET_STYLE_MASK, styleMask | NS_WINDOW_STYLE_FULL_SIZE_CONTENT_VIEW);
    }

    public void titleVisibility(final boolean visible) {
        msgSend(this.id, Meta.TITLE_VISIBILITY, visible ? 0 : 1);
    }

    public void addToolbar() {
        final var toolbar = NSToolbar.create();
        msgSend(this.id, Meta.TOOLBAR, toolbar.id());
    }

    public NSView contentView() {
        return NSView.from(msgSend(this.id, Meta.CONTENT_VIEW));
    }

    public void toggleToolbarShown() {
        msgSend(this.id, Meta.TOGGLE_TOOLBAR);
    }

    protected enum Meta {
        ;
        public static final MemorySegment TOOLBAR, TITLEBAR_APPEARS_TRANSPARENT,
                STYLE_MASK, SET_STYLE_MASK, TITLE_VISIBILITY,
                TITLE, TOGGLE_TOOLBAR, CONTENT_VIEW;

        static {
            try (final var arena = Arena.ofConfined()) {
                TOOLBAR = registerName(arena, "setToolbar:");
                TITLEBAR_APPEARS_TRANSPARENT = registerName(arena, "setTitlebarAppearsTransparent:");
                STYLE_MASK = registerName(arena, "styleMask");
                CONTENT_VIEW = registerName(arena, "contentView");
                SET_STYLE_MASK = registerName(arena, "setStyleMask:");
                TITLE_VISIBILITY = registerName(arena, "setTitleVisibility:");
                TITLE = registerName(arena, "title");
                TOGGLE_TOOLBAR = registerName(arena, "toggleToolbarShown:");
            }
        }
    }
}
