package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(Enchantment.class)
public interface EnchantmentAccessor {
    @Accessor("ENCHANTMENT_MAP")
    static Map<Identifier, Enchantment> ENCHANTMENT_MAP() {
        throw new Error();
    }
}
