package dev.u9g.minecraftdatagenerator.mixin.accessor;

import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(Biome.class)
public interface BiomeAccessor {
    @Accessor("BIOMESET")
    static Set<Biome> BIOMESET() {
        throw new IllegalStateException();
    }

    @Accessor("waterColor")
    int waterColor();

    @Accessor("name")
    String name();
}
