package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Biome.class)
public interface BiomeAccessor {
    @Accessor("waterColor")
    int waterColor();

    @Accessor("name")
    String name();
}
