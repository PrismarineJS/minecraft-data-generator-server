package dev.u9g.minecraftdatagenerator.clientsideannoyances;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RenderBlockView;
import org.jetbrains.annotations.Nullable;

public interface BlockColorable {
    int getColor(BlockState state, @Nullable RenderBlockView renderView, @Nullable BlockPos pos, int i);
}
