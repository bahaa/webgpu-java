package io.github.bahaa.webgpu.api;

import java.lang.foreign.MemorySegment;

public interface NativeObject extends AutoCloseable {
    MemorySegment pointer();

    /// Overridden here to remove `throws Exception`.
    @Override
    void close();
}
