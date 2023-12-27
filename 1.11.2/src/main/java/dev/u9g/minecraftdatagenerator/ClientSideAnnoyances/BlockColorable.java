package dev.u9g.minecraftdatagenerator.ClientSideAnnoyances;

import dev.u9g.minecraftdatagenerator.util.EmptyBlockView;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public interface BlockColorable {
    int method_12155(BlockState blockState, @Nullable EmptyBlockView blockView, @Nullable BlockPos blockPos, int i);
}
