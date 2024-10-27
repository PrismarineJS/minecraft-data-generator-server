package dev.u9g.minecraftdatagenerator.mixin.accessor;

import net.minecraft.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Enchantment.class)
public interface EnchantmentAccessor {
    @Accessor("ALL_ENCHANTMENTS")
    static Enchantment[] ALL_ENCHANTMENTS() {
        throw new IllegalStateException();
    }
}
