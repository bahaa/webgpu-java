package io.github.bahaa.webgpu.samples.glfw.ffm;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

@SuppressWarnings("PMD")
public class glfw3native_h extends glfw3_h {

    /**
     * {@snippet lang = c:
     * int glfwGetCocoaWindow()
     *}
     */
    public static MemorySegment glfwGetCocoaWindow(final MemorySegment window) {
        final var mh$ = glfwGetCocoaWindow.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("glfwGetCocoaWindow");
            }
            return (MemorySegment) mh$.invokeExact(window);
        } catch (final Error | RuntimeException ex) {
            throw ex;
        } catch (final Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }

    /**
     * {@snippet lang = c:
     * int glfwGetWin32Window()
     *}
     */
    public static MemorySegment glfwGetWin32Window(final MemorySegment window) {
        final var mh$ = glfwGetWin32Window.HANDLE;
        try {
            if (TRACE_DOWNCALLS) {
                traceDowncall("glfwGetWin32Window");
            }
            return (MemorySegment) mh$.invokeExact(window);
        } catch (final Error | RuntimeException ex) {
            throw ex;
        } catch (final Throwable ex$) {
            throw new AssertionError("should not reach here", ex$);
        }
    }

    private enum glfwGetCocoaWindow {
        ;
        public static final FunctionDescriptor DESC = FunctionDescriptor.of(
                glfw3_h.C_POINTER, glfw3_h.C_POINTER);

        public static final MemorySegment ADDR = SYMBOL_LOOKUP.findOrThrow("glfwGetCocoaWindow");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);
    }

    private enum glfwGetWin32Window {
        ;
        public static final FunctionDescriptor DESC = FunctionDescriptor.of(
                glfw3_h.C_POINTER, glfw3_h.C_POINTER);

        public static final MemorySegment ADDR = SYMBOL_LOOKUP.findOrThrow("glfwGetWin32Window");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);
    }
}
