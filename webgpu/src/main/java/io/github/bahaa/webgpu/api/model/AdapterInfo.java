package io.github.bahaa.webgpu.api.model;

public record AdapterInfo(String vendor, String architecture, String device, String description,
                          BackendType backendType, AdapterType adapterType, int vendorID, int deviceID) {
}
