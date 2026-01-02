package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.*;
import io.github.bahaa.webgpu.api.model.*;

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
    public synchronized RenderPassEncoder beginRenderPass(final RenderPassDescriptor descriptor) {
        try (final var arena = Arena.ofConfined()) {
            final var pass = wgpuCommandEncoderBeginRenderPass(this.pointer(), descriptor == null ?
                    MemorySegment.NULL : descriptor.toSegment(arena));
            assertObject(pass, "wgpuCommandEncoderBeginRenderPass");
            return RenderPassEncoderImpl.from(pass);
        }
    }

    @Override
    public synchronized ComputePassEncoder beginComputePass(final ComputePassDescriptor descriptor) {
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
    public synchronized void clearBuffer(final Buffer buffer, final long offset, final long size) {
        wgpuCommandEncoderClearBuffer(pointer(), buffer.pointer(), offset, size);
    }

    @Override
    public synchronized void copyBufferToBuffer(final Buffer source, final long sourceOffset, final Buffer destination,
                                                final long destinationOffset,
                                                final long size) {
        wgpuCommandEncoderCopyBufferToBuffer(this.pointer(),
                source.pointer(), sourceOffset, destination.pointer(), destinationOffset, size);

    }

    @Override
    public synchronized void copyBufferToTexture(final TexelCopyBufferInfo source,
                                                 final TexelCopyTextureInfo destination,
                                                 final Extent3D copySize) {
        try (final var arena = Arena.ofConfined()) {
            wgpuCommandEncoderCopyBufferToTexture(this.pointer(), source.toSegmentAddress(arena),
                    destination.toSegmentAddress(arena), copySize.toSegmentAddress(arena));
        }
    }

    @Override
    public synchronized void copyTextureToBuffer(final TexelCopyTextureInfo source,
                                                 final TexelCopyBufferInfo destination,
                                                 final Extent3D copySize) {
        try (final var arena = Arena.ofConfined()) {
            wgpuCommandEncoderCopyTextureToBuffer(this.pointer(), source.toSegmentAddress(arena),
                    destination.toSegmentAddress(arena), copySize.toSegmentAddress(arena));
        }
    }

    @Override
    public synchronized void copyTextureToTexture(final TexelCopyTextureInfo source,
                                                  final TexelCopyTextureInfo destination,
                                                  final Extent3D copySize) {
        try (final var arena = Arena.ofConfined()) {
            wgpuCommandEncoderCopyTextureToTexture(this.pointer(), source.toSegmentAddress(arena),
                    destination.toSegmentAddress(arena), copySize.toSegmentAddress(arena));
        }
    }

    @Override
    public synchronized void insertDebugMarker(final String markerLabel) {
        try (final var arena = Arena.ofConfined()) {
            wgpuCommandEncoderInsertDebugMarker(this.pointer(), StringView.from(markerLabel).toSegment(arena));
        }
    }

    @Override
    public synchronized void popDebugGroup() {
        wgpuCommandEncoderPopDebugGroup(this.pointer());
    }

    @Override
    public synchronized void pushDebugGroup(final String groupLabel) {
        try (final var arena = Arena.ofConfined()) {
            wgpuCommandEncoderPushDebugGroup(this.pointer(), StringView.from(groupLabel).toSegment(arena));
        }
    }

    @Override
    public synchronized void resolveQuerySet(final QuerySet querySet, final int firstQuery, final int queryCount,
                                             final Buffer destination,
                                             final long destinationOffset) {
        wgpuCommandEncoderResolveQuerySet(this.pointer(), querySet.pointer(), firstQuery, queryCount,
                destination.pointer(), destinationOffset);
    }

    @Override
    public synchronized void writeTimestamp(final QuerySet querySet, final int queryIndex) {
        wgpuCommandEncoderWriteTimestamp(this.pointer(), querySet.pointer(), queryIndex);
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
