package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(StatusEffect.class)
public interface StatusEffectAccessor {
    @Accessor("negative")
    boolean negative();

    @Accessor("STATUS_EFFECTS_BY_ID")
    public static Map<Identifier, StatusEffect> STATUS_EFFECTS_BY_ID() {throw new Error();}
}
