package dev.u9g.minecraftdatagenerator.mixin.accessor;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(Enchantment.class)
public interface EnchantmentAccessor {
    @Accessor("ALL_ENCHANTMENTS")
    public static Enchantment[] ALL_ENCHANTMENTS(){throw new Error();}
}
