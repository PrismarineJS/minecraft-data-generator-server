package dev.u9g.minecraftdatagenerator.clientsideannoyances;

import dev.u9g.minecraftdatagenerator.util.EmptyBlockView;
import net.minecraft.block.*;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.util.math.BlockPos;

import static net.minecraft.block.RedstoneWireBlock.POWER;

public class BiomeBlockColors {
    // generated manually from each Biome's Biome#getBlockColor
    public static int getBlockColor(Block block, BlockState state) {
        if (block instanceof AbstractFluidBlock) {
            return BiomeColors.getWaterColor(EmptyBlockView.INSTANCE, BlockPos.ORIGIN);
        } else if (block instanceof AttachedStemBlock) {
            return getAttachedStemColor(state, block);
        } else if (block instanceof DoublePlantBlock) {
            EmptyBlockView bv = new EmptyBlockView() {
                @Override
                public BlockState getBlockState(BlockPos pos) {
                    return state;
                }
            };
            DoublePlantBlock.DoublePlantType doublePlantType = ((DoublePlantBlock) block).getVariant(bv, BlockPos.ORIGIN);
            if (doublePlantType == DoublePlantBlock.DoublePlantType.GRASS || doublePlantType == DoublePlantBlock.DoublePlantType.FERN) {
                return BiomeColors.getGrassColor(bv, BlockPos.ORIGIN);
            }
        } else if (block instanceof FlowerPotBlock) {
            // FIXME: Yeah, flower pot block's color depends on the block inside of it, since we dont compute that, let's just not do anything
//            Item item;
//            BlockEntity blockEntity = view.getBlockEntity(pos);
//            if (blockEntity instanceof FlowerPotBlockEntity && (item = ((FlowerPotBlockEntity)blockEntity).getItem()) instanceof BlockItem) {
//                return Block.getBlockFromItem(item).getBlockColor(view, pos, id);
//            }
        } else if (block instanceof GrassBlock) {
            return BiomeColors.getGrassColor(EmptyBlockView.INSTANCE, BlockPos.ORIGIN);
        } else if (block instanceof Leaves1Block) {
            return getLeaves1Color(state, block);
        } else if (block instanceof LeavesBlock) {
            return getLeavesColor();
        } else if (block instanceof RedstoneWireBlock) {
            if (state.getBlock() == block) {
                return ServerSideRedstoneWireBlock.getWireColor(state.get(POWER));
            }
        } else if (block instanceof SugarCaneBlock || block instanceof TallPlantBlock) {
            return GrassColors.getGrassColor(EmptyBlockView.INSTANCE.getBiome(BlockPos.ORIGIN));
        } else if (block instanceof VineBlock) {
            return dev.u9g.minecraftdatagenerator.clientsideannoyances.FoliageColors.getFoliageColor(EmptyBlockView.INSTANCE.getBiome(BlockPos.ORIGIN));
        }
        return 0xFFFFFF;
    }

    private static int getLeaves1Color(BlockState blockState, Block block) {
        if (blockState.getBlock() == block) {
            PlanksBlock.WoodType woodType = blockState.get(Leaves1Block.VARIANT);
            if (woodType == PlanksBlock.WoodType.SPRUCE) {
                return net.minecraft.client.color.world.FoliageColors.getSpruceColor();
            }
            if (woodType == PlanksBlock.WoodType.BIRCH) {
                return FoliageColors.getBirchColor();
            }
        }
        return getLeavesColor();
    }

    private static int getLeavesColor() {
        return FoliageColors.getColor(0.5, 1.0);
    }

    private static int getAttachedStemColor(BlockState state, Block block) {
        if (state.getBlock() != block) {
            return 0xFFFFFF;
        }
        int i = state.get(AttachedStemBlock.AGE);
        int j = i * 32;
        int k = 255 - i * 8;
        int l = i * 4;
        return j << 16 | k << 8 | l;
    }
}
