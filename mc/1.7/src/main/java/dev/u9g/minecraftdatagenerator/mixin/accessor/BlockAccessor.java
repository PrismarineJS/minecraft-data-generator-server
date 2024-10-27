package dev.u9g.minecraftdatagenerator.mixin.accessor;

import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Block.class)
public interface BlockAccessor {
    @Accessor("blastResistance")
    float getBlastResistance();
}
