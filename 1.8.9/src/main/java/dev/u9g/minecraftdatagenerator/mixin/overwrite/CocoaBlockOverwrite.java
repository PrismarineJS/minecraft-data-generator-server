package dev.u9g.minecraftdatagenerator.mixin.overwrite;

import net.minecraft.block.BlockState;
import net.minecraft.block.CocoaBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(CocoaBlock.class)
public class CocoaBlockOverwrite {
    private static Box collisionBox(BlockState state) {
        Direction direction = state.get(CocoaBlock.FACING);
        int i = state.get(CocoaBlock.AGE);
        int j = 4 + i * 2;
        int k = 5 + i * 2;
        float f = (float) j / 2.0f;
        switch (direction) {
            case SOUTH: {
                return new Box((8.0f - f) / 16.0f, (12.0f - (float) k) / 16.0f, (15.0f - (float) j) / 16.0f, (8.0f + f) / 16.0f, 0.75f, 0.9375f);
            }
            case NORTH: {
                return new Box((8.0f - f) / 16.0f, (12.0f - (float) k) / 16.0f, 0.0625f, (8.0f + f) / 16.0f, 0.75f, (1.0f + (float) j) / 16.0f);
            }
            case WEST: {
                return new Box(0.0625f, (12.0f - (float) k) / 16.0f, (8.0f - f) / 16.0f, (1.0f + (float) j) / 16.0f, 0.75f, (8.0f + f) / 16.0f);
            }
            case EAST: {
                return new Box((15.0f - (float) j) / 16.0f, (12.0f - (float) k) / 16.0f, (8.0f - f) / 16.0f, 0.9375f, 0.75f, (8.0f + f) / 16.0f);
            }
        }
        throw new Error("Should never hit here");
    }

    /**
     * @author a
     * @reason a
     */
    @Overwrite
    public Box getCollisionBox(World world, BlockPos pos, BlockState state) {
        return collisionBox(state);
    }
}
