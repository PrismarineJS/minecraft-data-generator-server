package dev.u9g.minecraftdatagenerator.mixin.overwrite;

import dev.u9g.minecraftdatagenerator.util.EmptyBlockView;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

import static net.minecraft.block.StairsBlock.HALF;

@Mixin(StairsBlock.class)
public abstract class StairBlockOverwrite extends Block {
    protected StairBlockOverwrite(Material material) {
        super(material);
    }

    @Shadow public abstract void setBoundingBox(BlockView view, BlockPos pos);

    @Shadow public abstract boolean method_8911(BlockView blockView, BlockPos blockPos);

    /**
     * @author a
     * @reason a
     */
    @Overwrite
    public void appendCollisionBoxes(World world, BlockPos pos, BlockState state, Box box, List<Box> list, Entity entity) {
        list.add(someFuncThatIRemade(state));
        super.appendCollisionBoxes(world, pos, state, box, list, entity);
        EmptyBlockView bv = new EmptyBlockView() {
            @Override
            public BlockState getBlockState(BlockPos pos) {
                return state;
            }
        };
        boolean bl = this.method_8911(bv, pos);
        list.add(this.getCollisionBox(world, pos, state));
        if (bl && calculateWhichTypeOfStairThisIsButDoesntWorkBecauseWeDontHaveAdjBlocks(state)) {
            list.add(this.getCollisionBox(world, pos, state));
        }
        this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    public Box someFuncThatIRemade(BlockState state) {
        if (state.get(HALF) == StairsBlock.Half.TOP) {
            return new Box(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
        } else {
            return new Box(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
    }

    public boolean calculateWhichTypeOfStairThisIsButDoesntWorkBecauseWeDontHaveAdjBlocks(BlockState state) {
        EmptyBlockView blockView = new EmptyBlockView() {
            @Override
            public BlockState getBlockState(BlockPos pos) {
                return state;
            }
        };
        BlockState blockState2;
        Block block;
        BlockState blockState = blockView.getBlockState(BlockPos.ORIGIN);
        Direction direction = blockState.get(StairsBlock.FACING);
        StairsBlock.Half half = blockState.get(HALF);
        boolean bl = half == StairsBlock.Half.TOP;
        float f = 0.5f;
        float g = 1.0f;
        if (bl) {
            f = 0.0f;
            g = 0.5f;
        }
        float h = 0.0f;
        float i = 0.5f;
        float j = 0.5f;
        float k = 1.0f;
        boolean bl2 = false;
        if (direction == Direction.EAST) {
            BlockState blockState22 = blockView.getBlockState(BlockPos.ORIGIN);
            Block block2 = blockState22.getBlock();
            if (StairsBlock.method_6513(block2) && half == blockState22.get(HALF)) {
                Direction direction2 = blockState22.get(StairsBlock.FACING);
                if (direction2 == Direction.NORTH && !StairsBlock.method_8907(blockView, BlockPos.ORIGIN, blockState)) {
                    j = 0.0f;
                    k = 0.5f;
                    bl2 = true;
                } else if (direction2 == Direction.SOUTH && !StairsBlock.method_8907(blockView, BlockPos.ORIGIN, blockState)) {
                    j = 0.5f;
                    k = 1.0f;
                    bl2 = true;
                }
            }
        } else if (direction == Direction.WEST) {
            BlockState blockState23 = blockView.getBlockState(BlockPos.ORIGIN);
            Block block3 = blockState23.getBlock();
            if (StairsBlock.method_6513(block3) && half == blockState23.get(HALF)) {
                h = 0.5f;
                i = 1.0f;
                Direction direction2 = blockState23.get(StairsBlock.FACING);
                if (direction2 == Direction.NORTH && !StairsBlock.method_8907(blockView, BlockPos.ORIGIN, blockState)) {
                    j = 0.0f;
                    k = 0.5f;
                    bl2 = true;
                } else if (direction2 == Direction.SOUTH && !StairsBlock.method_8907(blockView, BlockPos.ORIGIN, blockState)) {
                    j = 0.5f;
                    k = 1.0f;
                    bl2 = true;
                }
            }
        } else if (direction == Direction.SOUTH) {
            BlockState blockState24 = blockView.getBlockState(BlockPos.ORIGIN);
            Block block4 = blockState24.getBlock();
            if (StairsBlock.method_6513(block4) && half == blockState24.get(HALF)) {
                j = 0.0f;
                k = 0.5f;
                Direction direction2 = blockState24.get(StairsBlock.FACING);
                if (direction2 == Direction.WEST && !StairsBlock.method_8907(blockView, BlockPos.ORIGIN, blockState)) {
                    bl2 = true;
                } else if (direction2 == Direction.EAST && !StairsBlock.method_8907(blockView, BlockPos.ORIGIN, blockState)) {
                    h = 0.5f;
                    i = 1.0f;
                    bl2 = true;
                }
            }
        } else if (direction == Direction.NORTH && StairsBlock.method_6513(block = (blockState2 = blockView.getBlockState(BlockPos.ORIGIN)).getBlock()) && half == blockState2.get(HALF)) {
            Direction direction2 = blockState2.get(StairsBlock.FACING);
            if (direction2 == Direction.WEST && !StairsBlock.method_8907(blockView, BlockPos.ORIGIN, blockState)) {
                bl2 = true;
            } else if (direction2 == Direction.EAST && !StairsBlock.method_8907(blockView, BlockPos.ORIGIN, blockState)) {
                h = 0.5f;
                i = 1.0f;
                bl2 = true;
            }
        }
        if (bl2) {
            this.setBoundingBox(h, f, j, i, g, k);
        }
        return bl2;
    }
}
