package dev.u9g.minecraftdatagenerator.clientsideannoyances;

import dev.u9g.minecraftdatagenerator.util.EmptyBlockView;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface BlockColorable {
    int method_12155(BlockState blockState, @Nullable EmptyBlockView blockView, @Nullable BlockPos blockPos, int i);
}
