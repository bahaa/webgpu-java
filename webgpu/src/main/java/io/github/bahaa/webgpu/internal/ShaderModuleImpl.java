package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.ShaderModule;
import io.github.bahaa.webgpu.api.WebGpuException;
import io.github.bahaa.webgpu.api.model.CompilationInfo;
import io.github.bahaa.webgpu.api.model.CompilationMessage;
import io.github.bahaa.webgpu.api.model.CompilationMessageType;
import io.github.bahaa.webgpu.api.model.StringView;
import io.github.bahaa.webgpu.ffm.WGPUCompilationInfo;
import io.github.bahaa.webgpu.ffm.WGPUCompilationInfoCallback;
import io.github.bahaa.webgpu.ffm.WGPUCompilationInfoCallbackInfo;
import io.github.bahaa.webgpu.ffm.WGPUCompilationMessage;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static io.github.bahaa.webgpu.ffm.webgpu_h.*;

class ShaderModuleImpl extends ObjectBaseImpl implements ShaderModule {

    protected ShaderModuleImpl(final MemorySegment pointer) {
        super(pointer);
    }

    static ShaderModuleImpl from(final MemorySegment pointer) {
        return new ShaderModuleImpl(pointer);
    }

    @Override
    protected ObjectCleaner cleaner() {
        return new Cleaner(this.pointer());
    }

    @Override
    public void label(final String label) {
        try (final var arena = Arena.ofConfined()) {
            wgpuShaderModuleSetLabel(pointer(), StringView.from(label).toSegment(arena));
        }
    }

    @Override
    public CompletableFuture<CompilationInfo> getCompilationInfo() {
        final var future = new CompletableFuture<CompilationInfo>();

        final WGPUCompilationInfoCallback.Function callback = (status, compilationInfo, userdata1, userdata2) -> {
            if (status == WGPUCompilationInfoRequestStatus_Success()) {
                final var count = WGPUCompilationInfo.messageCount(compilationInfo);
                final var nativeMessages = WGPUCompilationInfo.messages(compilationInfo);
                final var messages = new CompilationMessage[(int) count];
                final var messageByteSize = WGPUCompilationMessage.layout().byteSize();
                for (var i = 0; i < count; i++) {
                    final var messageStruct = nativeMessages.asSlice(messageByteSize * i, messageByteSize);
                    messages[i] = new CompilationMessage(
                            StringView.from(WGPUCompilationMessage.message(messageStruct)).value(),
                            CompilationMessageType.fromValue(WGPUCompilationMessage.type(messageStruct)),
                            WGPUCompilationMessage.lineNum(messageStruct),
                            WGPUCompilationMessage.linePos(messageStruct),
                            WGPUCompilationMessage.offset(messageStruct),
                            WGPUCompilationMessage.length(messageStruct)
                    );
                }
                future.complete(new CompilationInfo(List.of(messages)));
            } else {
                future.completeExceptionally(
                        new WebGpuException("Error getting compilation info: %d".formatted(status)));
            }
        };

        try (final var arena = Arena.ofConfined()) {
            final var stub = WGPUCompilationInfoCallback.allocate(callback, arena);
            final var callbackInfo = WGPUCompilationInfoCallbackInfo.allocate(arena);
            WGPUCompilationInfoCallbackInfo.callback(callbackInfo, stub);
            var _ = wgpuShaderModuleGetCompilationInfo(arena, this.pointer(), callbackInfo);
        }

        return future;
    }

    private static class Cleaner extends ObjectCleaner {

        protected Cleaner(final MemorySegment pointer) {
            super(pointer);
        }

        @Override
        protected void clean(final MemorySegment pointer) {
            wgpuShaderModuleRelease(pointer);
        }
    }
}
