package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.sound.Sound;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Sound.class)
public interface SoundAccessor {
    @Accessor("id")
    Identifier id();
}
