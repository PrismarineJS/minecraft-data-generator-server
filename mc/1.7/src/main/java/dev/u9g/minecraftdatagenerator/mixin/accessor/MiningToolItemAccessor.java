package dev.u9g.minecraftdatagenerator.mixin.accessor;

import net.minecraft.block.Block;
import net.minecraft.item.ToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(ToolItem.class)
public interface MiningToolItemAccessor {

    @Accessor
    Set<Block> getEffectiveBlocks();
}
