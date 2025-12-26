package io.github.bahaa.webgpu.api;

import io.github.bahaa.webgpu.api.model.DeviceDescriptor;

import java.util.concurrent.CompletableFuture;

public interface Adapter extends ObjectBase {

    CompletableFuture<Device> requestDevice(final DeviceDescriptor deviceDescriptor);
}
