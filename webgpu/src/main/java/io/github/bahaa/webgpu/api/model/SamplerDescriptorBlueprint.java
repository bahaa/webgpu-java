package io.github.bahaa.webgpu.api.model;

import io.github.bahaa.webgpu.ffm.WGPUSamplerDescriptor;
import io.helidon.builder.api.Option;
import io.helidon.builder.api.Prototype;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

@Prototype.Blueprint
interface SamplerDescriptorBlueprint extends StructBlueprint {

    Optional<String> label();

    @Option.Default("CLAMP_TO_EDGE")
    AddressMode addressModeU();

    @Option.Default("CLAMP_TO_EDGE")
    AddressMode addressModeV();

    @Option.Default("CLAMP_TO_EDGE")
    AddressMode addressModeW();

    @Option.Default("NEAREST")
    FilterMode magFilter();

    @Option.Default("NEAREST")
    FilterMode minFilter();

    @Option.Default("NEAREST")
    FilterMode mipmapFilter();

    @Option.DefaultCode("0")
    float lodMinClamp();

    @Option.DefaultCode("32f")
    float lodMaxClamp();

    Optional<CompareFunction> compare();

    @Option.DefaultInt(1)
    short maxAnisotropy();

    @Override
    default MemorySegment toSegment(final Arena arena) {
        final var struct = WGPUSamplerDescriptor.allocate(arena);
        updateSegment(arena, struct);
        return struct;
    }

    @Override
    default void updateSegment(final Arena arena, final MemorySegment struct) {
        label().ifPresent(label -> WGPUSamplerDescriptor.label(struct, StringView.from(label).toSegment(arena)));
        WGPUSamplerDescriptor.addressModeU(struct, addressModeU().value());
        WGPUSamplerDescriptor.addressModeV(struct, addressModeV().value());
        WGPUSamplerDescriptor.addressModeW(struct, addressModeW().value());
        WGPUSamplerDescriptor.magFilter(struct, magFilter().value());
        WGPUSamplerDescriptor.minFilter(struct, minFilter().value());
        WGPUSamplerDescriptor.mipmapFilter(struct, mipmapFilter().value());
        WGPUSamplerDescriptor.lodMinClamp(struct, lodMinClamp());
        WGPUSamplerDescriptor.lodMaxClamp(struct, lodMaxClamp());
        compare().ifPresent(compare -> WGPUSamplerDescriptor.compare(struct, compare.value()));
        WGPUSamplerDescriptor.maxAnisotropy(struct, maxAnisotropy());
    }
}
