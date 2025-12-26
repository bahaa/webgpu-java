package io.github.bahaa.webgpu.api.model;

public interface SurfaceSource extends StructBlueprint {

    static SurfaceSourceMetalLayer.Builder metalLayer() {
        return SurfaceSourceMetalLayer.builder();
    }

    static SurfaceSourceWindowsHWND.Builder windowsHWND() {
        return SurfaceSourceWindowsHWND.builder();
    }
}
