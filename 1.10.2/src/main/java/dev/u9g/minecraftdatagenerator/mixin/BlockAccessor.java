package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Block.class)
public interface BlockAccessor {
    @Accessor("blastResistance")
    float getBlastResistance();
}
