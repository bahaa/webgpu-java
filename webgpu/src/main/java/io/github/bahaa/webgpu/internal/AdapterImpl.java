package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.Adapter;
import io.github.bahaa.webgpu.api.Device;
import io.github.bahaa.webgpu.api.model.DeviceDescriptor;
import io.github.bahaa.webgpu.ffm.WGPURequestDeviceCallback;
import io.github.bahaa.webgpu.ffm.WGPURequestDeviceCallbackInfo;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.concurrent.CompletableFuture;

import static io.github.bahaa.webgpu.ffm.webgpu_h.*;

class AdapterImpl extends ObjectBaseImpl implements Adapter {

    protected AdapterImpl(final MemorySegment pointer) {
        super(pointer);
    }

    public static AdapterImpl from(final MemorySegment pointer) {
        return new AdapterImpl(pointer);
    }

    @Override
    protected ObjectCleaner cleaner() {
        return new Cleaner(this.pointer());
    }

    @Override
    public CompletableFuture<Device> requestDevice(final DeviceDescriptor deviceDescriptor) {
        final var arena = Arena.ofAuto();
        final var future = new CompletableFuture<Device>();

        final WGPURequestDeviceCallback.Function callback = (status, device, message,
                                                             userdata1, userdata2) -> {
            if (status == WGPURequestDeviceStatus_Success()) {
                future.complete(DeviceImpl.from(device));
            } else {
                future.completeExceptionally(new RuntimeException("WGPURequestDeviceStatus: %d"
                        .formatted(status)));
            }
        };

        final var callbackStub = WGPURequestDeviceCallback.allocate(callback, arena);

        final var callbackInfo = WGPURequestDeviceCallbackInfo.allocate(arena);
        WGPURequestDeviceCallbackInfo.callback(callbackInfo, callbackStub);

        var _ = wgpuAdapterRequestDevice(arena, this.pointer(),
                deviceDescriptor.toSegment(arena), callbackInfo);

        return future;
    }

    private static class Cleaner extends ObjectCleaner {

        protected Cleaner(final MemorySegment pointer) {
            super(pointer);
        }

        @Override
        protected void clean(final MemorySegment pointer) {
            wgpuAdapterRelease(pointer);
        }
    }
}
