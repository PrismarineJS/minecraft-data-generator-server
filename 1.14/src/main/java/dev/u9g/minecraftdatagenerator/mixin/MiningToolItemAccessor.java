package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.MiningToolItem;
import net.minecraft.tag.Tag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(MiningToolItem.class)
public interface MiningToolItemAccessor {

    @Accessor
    Set<Block> getEffectiveBlocks();
}
