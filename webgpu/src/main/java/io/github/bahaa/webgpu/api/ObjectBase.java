package io.github.bahaa.webgpu.api;

import java.lang.foreign.MemorySegment;

public interface ObjectBase extends AutoCloseable {

    MemorySegment pointer();

    @Override
    void close();
}
