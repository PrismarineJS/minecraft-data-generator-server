package dev.u9g.minecraftdatagenerator.mixin.accessor;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(StatusEffect.class)
public interface StatusEffectAccessor {
    @Accessor("negative")
    boolean negative();

    @Accessor("STATUS_EFFECTS")
    public static StatusEffect[] STATUS_EFFECTS() {throw new Error();}
}
