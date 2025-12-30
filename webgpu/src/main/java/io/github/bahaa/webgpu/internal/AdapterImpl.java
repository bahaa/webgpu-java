package io.github.bahaa.webgpu.internal;

import io.github.bahaa.webgpu.api.Adapter;
import io.github.bahaa.webgpu.api.Device;
import io.github.bahaa.webgpu.api.model.*;
import io.github.bahaa.webgpu.ffm.WGPUAdapterInfo;
import io.github.bahaa.webgpu.ffm.WGPURequestDeviceCallback;
import io.github.bahaa.webgpu.ffm.WGPURequestDeviceCallbackInfo;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static io.github.bahaa.webgpu.ffm.webgpu_h.*;

class AdapterImpl extends NativeObjectImpl implements Adapter {

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
    public CompletableFuture<Device> requestDevice(final DeviceDescriptor descriptor) {
        Objects.requireNonNull(descriptor, "descriptor is null");
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
                descriptor.toSegment(arena), callbackInfo);

        return future;
    }

    @Override
    public AdapterInfo adapterInfo() {
        try (final var arena = Arena.ofConfined()) {
            final var infoStruct = WGPUAdapterInfo.allocate(arena);
            wgpuAdapterGetInfo(pointer(), infoStruct);
            final var info = new AdapterInfo(
                    StringView.from(WGPUAdapterInfo.vendor(infoStruct)).value(),
                    StringView.from(WGPUAdapterInfo.architecture(infoStruct)).value(),
                    StringView.from(WGPUAdapterInfo.device(infoStruct)).value(),
                    StringView.from(WGPUAdapterInfo.description(infoStruct)).value(),
                    BackendType.fromValue(WGPUAdapterInfo.backendType(infoStruct)),
                    AdapterType.fromValue(WGPUAdapterInfo.adapterType(infoStruct)),
                    WGPUAdapterInfo.vendorID(infoStruct),
                    WGPUAdapterInfo.deviceID(infoStruct)
            );
            wgpuAdapterInfoFreeMembers(infoStruct);
            return info;
        }
    }

    @Override
    public boolean hasFeature(final FeatureName feature) {
        Objects.requireNonNull(feature, "feature is null");
        return wgpuAdapterHasFeature(pointer(), feature.value()) > 0;
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
