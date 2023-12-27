package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.item.VariantBlockItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(VariantBlockItem.class)
public interface VariantBlockItemAccessor {
    @Accessor("field_5448")
    String[] variants();
}
