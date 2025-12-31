package io.github.bahaa.webgpu.samples;

import io.github.bahaa.webgpu.api.*;
import io.github.bahaa.webgpu.api.model.*;
import io.github.bahaa.webgpu.samples.platform.macos.appkit.NSWindow;
import io.github.bahaa.webgpu.samples.platform.macos.ca.CAMetalLayer;
import io.github.bahaa.webgpu.tools.LibraryLoader;
import io.github.bahaa.webgpu.tools.Platform;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;

import static io.github.bahaa.webgpu.samples.glfw.ffm.glfw3_h.*;
import static io.github.bahaa.webgpu.samples.glfw.ffm.glfw3native_h.glfwGetCocoaWindow;
import static java.lang.foreign.MemorySegment.NULL;

public abstract class SampleBase {

    protected static final int DEFAULT_WIDTH = 800;
    protected static final int DEFAULT_HEIGHT = 800;

    protected void run(final String[] args) {
        LibraryLoader.loadLibrary("glfw.3");

        final var glfwVersion = glfwGetVersionString().getString(0);

        IO.println("GLFW Version: %s".formatted(glfwVersion));

        if (glfwInit() != GL_TRUE()) {
            IO.println("GLFW Initialization Failed!");
            System.exit(-1);
        }

        final var arena = Arena.ofAuto();

        try (final var instance = Instance.create()) {
            glfwWindowHint(GLFW_CLIENT_API(), GLFW_NO_API());
            glfwWindowHint(GLFW_RESIZABLE(), GLFW_TRUE());

            final var window = glfwCreateWindow(DEFAULT_WIDTH, DEFAULT_HEIGHT,
                    arena.allocateFrom(this.title()), NULL, NULL);

            if (window == NULL) {
                IO.println("GLFW Window Creation Failed!");
                System.exit(-1);
            }

            final var surface = createSurface(window, instance);

            final var adapter = instance.requestAdapter(RequestAdapterOptions.builder()
                            .compatibleSurface(surface)
                            .build())
                    .join();

            IO.println(adapter.adapterInfo());

            if (!adapter.hasFeature(FeatureName.BGRA8UNORM_STORAGE)) {
                IO.println("Adapter does not support BGRA8UNORM_STORAGE");
            }

            final var device = adapter.requestDevice(DeviceDescriptor.builder()
                            .build())
                    .join();

            final var queue = device.queue();

            final var capabilities = surface.capabilities(adapter);

            configureSurface(surface, device, capabilities, DEFAULT_WIDTH, DEFAULT_HEIGHT);

            setup(device, queue, capabilities);

            while (glfwWindowShouldClose(window) == 0) {
                glfwPollEvents();

                final var surfaceTexture = surface.currentTexture();
                final var texture = surfaceTexture.texture();

                switch (surfaceTexture.status()) {
                    case OUT_OF_MEMORY, DEVICE_LOST, FORCE32 -> throw new OutOfMemoryError("Out of memory");
                    case TIMEOUT, OUTDATED, LOST -> {
                        if (texture != null) {
                            texture.close();
                        }
                        final var width = arena.allocate(ValueLayout.JAVA_INT);
                        final var height = arena.allocate(ValueLayout.JAVA_INT);
                        glfwGetWindowSize(window, MemorySegment.ofAddress(width.address()),
                                MemorySegment.ofAddress(height.address()));

                        final var w = width.get(ValueLayout.JAVA_INT, 0);
                        final var h = height.get(ValueLayout.JAVA_INT, 0);

                        if (w != 0 && h != 0) {
                            configureSurface(surface, device, capabilities, w, h);
                        }
                        continue;
                    }
                }

                render(device, queue, surface, texture);

                // All WebGPU objects are auto-closable, but if you forgot to close them, they will be closed when
                // the object being garbage collected.
                texture.close();
            }

            surface.close();
            queue.close();
            device.close();
        } finally {
            glfwTerminate();
        }
    }

    protected abstract void setup(Device device, Queue queue, SurfaceCapabilities capabilities);

    protected abstract void render(Device device, Queue queue, Surface surface, Texture texture);

    protected abstract String title();

    private void configureSurface(final Surface surface, final Device device,
                                  final SurfaceCapabilities capabilities, final int width, final int height) {
        surface.configure(SurfaceConfiguration.builder()
                .device(device)
                .format(capabilities.getFormats().getFirst())
                .width(width)
                .height(height)
                .usage(EnumSet.of(TextureUsage.RENDER_ATTACHMENT))
                .build());
    }

    protected Surface createSurface(final MemorySegment window, final Instance instance) {
        return switch (Platform.currentOS()) {
            case WINDOWS, LINUX -> throw new UnsupportedOperationException("Not supported yet.");
            case MACOS -> createMetalSurface(window, instance);
            case OTHER -> throw new UnsupportedOperationException("Unknown operating system!");
        };
    }

    protected String loadFromClassPath(final String path) {

        try (final var is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {

            if (is == null) {
                throw new UncheckedIOException(new IOException("Resource not found: %s".formatted(path)));
            }

            return new String(is.readAllBytes(), StandardCharsets.UTF_8);

        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    protected float randomBetween(final float min, final float max) {
        return min + (float) Math.random() * (max - min);
    }

    protected ShaderModule loadShader(final Device device, final String path) {
        return device.createShaderModule(ShaderModuleDescriptor.builder()
                .label("Shader")
                .source(ShaderSource.wgsl()
                        .code(loadFromClassPath(path))
                        .build())
                .build());
    }

    private Surface createMetalSurface(final MemorySegment window, final Instance instance) {
        final var nsWindow = NSWindow.from(glfwGetCocoaWindow(window));
        final var layer = CAMetalLayer.layer();
        final var contentView = nsWindow.contentView();

        contentView.setWantsLayer(true);
        contentView.setLayer(layer);

        return instance.createSurface(SurfaceDescriptor.builder()
                .source(SurfaceSource.metalLayer()
                        .layer(layer.id())
                        .build())
                .build()
        );
    }
}
