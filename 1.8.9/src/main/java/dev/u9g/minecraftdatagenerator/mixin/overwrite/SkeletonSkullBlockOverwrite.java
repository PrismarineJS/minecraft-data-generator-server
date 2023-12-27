package dev.u9g.minecraftdatagenerator.mixin.overwrite;

import net.minecraft.block.BlockState;
import net.minecraft.block.SkeletonSkullBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SkeletonSkullBlock.class)
public class SkeletonSkullBlockOverwrite {
    public Box getCollisionBox(World world, BlockPos pos, BlockState state) {
        return boundingBox(state);
    }

    private static Box boundingBox(BlockState state) {
        switch (state.get(SkeletonSkullBlock.FACING)) {
            default: {
                return new Box(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
            }
            case NORTH: {
                return new Box(0.25f, 0.25f, 0.5f, 0.75f, 0.75f, 1.0f);
            }
            case SOUTH: {
                return new Box(0.25f, 0.25f, 0.0f, 0.75f, 0.75f, 0.5f);
            }
            case WEST: {
                return new Box(0.5f, 0.25f, 0.25f, 1.0f, 0.75f, 0.75f);
            }
            case EAST: {
                return new Box(0.0f, 0.25f, 0.25f, 0.5f, 0.75f, 0.75f);
            }
        }
    }
}
