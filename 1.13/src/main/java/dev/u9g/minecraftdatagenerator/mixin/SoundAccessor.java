package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.client.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SoundEvent.class)
public interface SoundAccessor {
    @Accessor("id")
    Identifier id();
}
