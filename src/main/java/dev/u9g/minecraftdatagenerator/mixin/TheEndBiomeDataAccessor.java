package dev.u9g.minecraftdatagenerator.mixin;

import net.fabricmc.fabric.impl.biome.TheEndBiomeData;
import net.fabricmc.fabric.impl.biome.WeightedPicker;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;


@Mixin(TheEndBiomeData.class)
public interface TheEndBiomeDataAccessor {
    @Accessor("END_BIOMES_MAP")
    public static Map<RegistryKey<Biome>, WeightedPicker<RegistryKey<Biome>>> END_BIOMES_MAP() {
        throw new IllegalStateException("Should never be called.");
    }
    @Accessor("END_MIDLANDS_MAP")
    public static Map<RegistryKey<Biome>, WeightedPicker<RegistryKey<Biome>>> END_MIDLANDS_MAP() {
        throw new IllegalStateException("Should never be called.");
    }
    @Accessor("END_BARRENS_MAP")
    public static Map<RegistryKey<Biome>, WeightedPicker<RegistryKey<Biome>>> END_BARRENS_MAP() {
        throw new IllegalStateException("Should never be called.");
    }
}