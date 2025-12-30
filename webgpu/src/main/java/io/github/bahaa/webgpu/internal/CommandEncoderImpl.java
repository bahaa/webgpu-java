package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.CommandBuffer;
import io.github.bahaa.webgpu.api.CommandEncoder;
import io.github.bahaa.webgpu.api.ComputePassEncoder;
import io.github.bahaa.webgpu.api.RenderPassEncoder;
import io.github.bahaa.webgpu.api.model.CommandBufferDescriptor;
import io.github.bahaa.webgpu.api.model.ComputePassDescriptor;
import io.github.bahaa.webgpu.api.model.RenderPassDescriptor;
import io.github.bahaa.webgpu.api.model.StringView;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static io.github.bahaa.webgpu.ffm.webgpu_h.*;

class CommandEncoderImpl extends ObjectBaseImpl implements CommandEncoder {

    protected CommandEncoderImpl(final MemorySegment pointer) {
        super(pointer);
    }

    static CommandEncoderImpl from(final MemorySegment pointer) {
        return new CommandEncoderImpl(pointer);
    }

    @Override
    protected ObjectCleaner cleaner() {
        return new Cleaner(this.pointer());
    }

    @Override
    public RenderPassEncoder beginRenderPass(final RenderPassDescriptor descriptor) {
        try (final var arena = Arena.ofConfined()) {
            final var pass = wgpuCommandEncoderBeginRenderPass(this.pointer(), descriptor == null ?
                    MemorySegment.NULL : descriptor.toSegment(arena));
            assertObject(pass, "wgpuCommandEncoderBeginRenderPass");
            return RenderPassEncoderImpl.from(pass);
        }
    }

    @Override
    public ComputePassEncoder beginComputePass(final ComputePassDescriptor descriptor) {
        try (final var arena = Arena.ofConfined()) {
            final var pass = wgpuCommandEncoderBeginComputePass(this.pointer(), descriptor == null ?
                    MemorySegment.NULL : descriptor.toSegment(arena));
            assertObject(pass, "wgpuCommandEncoderBeginComputePass");
            return ComputePassEncoderImpl.from(pass);
        }
    }

    @Override
    public CommandBuffer finish(final CommandBufferDescriptor descriptor) {
        try (final var arena = Arena.ofConfined()) {
            final var buffer = wgpuCommandEncoderFinish(this.pointer(), descriptor == null ?
                    MemorySegment.NULL : descriptor.toSegmentAddress(arena));
            assertObject(buffer, "wgpuCommandEncoderFinish");
            return CommandBufferImpl.from(buffer);
        }
    }

    @Override
    public void label(final String label) {
        try (final var arena = Arena.ofConfined()) {
            wgpuCommandEncoderSetLabel(pointer(), StringView.from(label).toSegment(arena));
        }
    }

    private static class Cleaner extends ObjectCleaner {

        protected Cleaner(final MemorySegment pointer) {
            super(pointer);
        }

        @Override
        protected void clean(final MemorySegment pointer) {
            wgpuCommandEncoderRelease(pointer);
        }
    }
}
