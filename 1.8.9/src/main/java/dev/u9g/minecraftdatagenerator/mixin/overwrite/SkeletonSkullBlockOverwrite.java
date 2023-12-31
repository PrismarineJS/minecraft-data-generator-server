package dev.u9g.minecraftdatagenerator.mixin.overwrite;

import net.minecraft.block.BlockState;
import net.minecraft.block.SkeletonSkullBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SkeletonSkullBlock.class)
public class SkeletonSkullBlockOverwrite {
    private static Box boundingBox(BlockState state) {
        return switch (state.get(SkeletonSkullBlock.FACING)) {
            default -> new Box(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
            case NORTH -> new Box(0.25f, 0.25f, 0.5f, 0.75f, 0.75f, 1.0f);
            case SOUTH -> new Box(0.25f, 0.25f, 0.0f, 0.75f, 0.75f, 0.5f);
            case WEST -> new Box(0.5f, 0.25f, 0.25f, 1.0f, 0.75f, 0.75f);
            case EAST -> new Box(0.0f, 0.25f, 0.25f, 0.5f, 0.75f, 0.75f);
        };
    }

    public Box getCollisionBox(World world, BlockPos pos, BlockState state) {
        return boundingBox(state);
    }
}
