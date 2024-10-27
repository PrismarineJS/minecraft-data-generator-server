package dev.u9g.minecraftdatagenerator.mixin.accessor;

import net.minecraft.entity.effect.StatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StatusEffect.class)
public interface StatusEffectAccessor {
    @Accessor("STATUS_EFFECTS")
    static StatusEffect[] STATUS_EFFECTS() {
        throw new IllegalStateException();
    }

    @Accessor("negative")
    boolean negative();
}
