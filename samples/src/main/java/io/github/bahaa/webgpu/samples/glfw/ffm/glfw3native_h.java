package io.github.bahaa.webgpu.samples.glfw.ffm;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

@SuppressWarnings("PMD")
public class glfw3native_h extends glfw3_h {

    /**
     * Function descriptor for:
     * {@snippet lang = c:
     * int glfwGetCocoaWindow()
     *}
     */
    public static FunctionDescriptor glfwGetCocoaWindow$descriptor() {
        return glfwGetCocoaWindow.DESC;
    }

    /**
     * Downcall method handle for:
     * {@snippet lang = c:
     * int glfwGetCocoaWindow()
     *}
     */
    public static MethodHandle glfwGetCocoaWindow$handle() {
        return glfwGetCocoaWindow.HANDLE;
    }

    /**
     * Address for:
     * {@snippet lang = c:
     * int glfwGetCocoaWindow()
     *}
     */
    public static MemorySegment glfwGetCocoaWindow$address() {
        return glfwGetCocoaWindow.ADDR;
    }

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

    private enum glfwGetCocoaWindow {
        ;
        public static final FunctionDescriptor DESC = FunctionDescriptor.of(
                glfw3_h.C_POINTER, glfw3_h.C_POINTER);

        public static final MemorySegment ADDR = SYMBOL_LOOKUP.findOrThrow("glfwGetCocoaWindow");

        public static final MethodHandle HANDLE = Linker.nativeLinker().downcallHandle(ADDR, DESC);
    }
}
