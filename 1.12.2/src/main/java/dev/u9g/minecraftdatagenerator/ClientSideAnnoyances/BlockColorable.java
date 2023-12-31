package dev.u9g.minecraftdatagenerator.ClientSideAnnoyances;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public interface BlockColorable {
    int method_12155(BlockState blockState, @Nullable BlockView blockView, @Nullable BlockPos blockPos, int i);
}
