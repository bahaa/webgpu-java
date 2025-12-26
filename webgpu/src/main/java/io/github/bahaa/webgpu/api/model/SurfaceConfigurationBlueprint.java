package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.api.Device;
import io.github.bahaa.webgpu.ffm.WGPUSurfaceConfiguration;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

@Prototype.Blueprint
interface SurfaceConfigurationBlueprint extends StructBlueprint {

    Device device();

    TextureFormat format();

    @Option.Singular("usage")
    EnumSet<TextureUsage> usage();

    int width();

    int height();

    @Option.Singular("viewFormat")
    List<TextureFormat> viewFormats();

    Optional<CompositeAlphaMode> alphaMode();

    Optional<PresentMode> presentMode();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUSurfaceConfiguration.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        WGPUSurfaceConfiguration.device(struct, device().pointer());
        WGPUSurfaceConfiguration.format(struct, format().value());

        WGPUSurfaceConfiguration.usage(struct, usage().stream()
                .mapToLong(TextureUsage::value)
                .reduce(0, (a, b) -> a | b));

        WGPUSurfaceConfiguration.width(struct, width());
        WGPUSurfaceConfiguration.height(struct, height());

        final var viewFormats = arena.allocate(ValueLayout.JAVA_INT, viewFormats().size());
        for (var i = 0; i < viewFormats().size(); i++) {
            viewFormats.setAtIndex(ValueLayout.JAVA_INT, i, viewFormats().get(i).value());
        }
        WGPUSurfaceConfiguration.viewFormats(struct, viewFormats);
        WGPUSurfaceConfiguration.viewFormatCount(struct, viewFormats().size());

        alphaMode().ifPresent(alphaMode -> WGPUSurfaceConfiguration.alphaMode(struct, alphaMode.value()));
        presentMode().ifPresent(presentMode -> WGPUSurfaceConfiguration.presentMode(struct, presentMode.value()));
    }
}
