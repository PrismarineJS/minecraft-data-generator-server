package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClampedEntityAttribute.class)
public interface ClampedEntityAttributeAccessor {
    @Accessor("minValue")
    double getMinValue();

    @Accessor("maxValue")
    double getMaxValue();
}
