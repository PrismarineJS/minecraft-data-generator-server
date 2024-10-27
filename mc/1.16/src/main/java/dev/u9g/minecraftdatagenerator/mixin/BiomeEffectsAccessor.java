package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.world.biome.BiomeEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BiomeEffects.class)
public interface BiomeEffectsAccessor {
    @Accessor("waterColor")
    int waterColor();
}
