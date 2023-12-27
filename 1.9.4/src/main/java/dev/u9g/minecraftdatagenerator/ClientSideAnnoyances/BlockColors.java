package dev.u9g.minecraftdatagenerator.ClientSideAnnoyances;

import dev.u9g.minecraftdatagenerator.util.EmptyBlockView;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.FlowerPotBlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.collection.IdList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class BlockColors {
    private final IdList<BlockColorable> BlockColor2Id = new IdList(32);

    public BlockColors() {
    }

    public static BlockColors create() {
        final BlockColors blockColors = new BlockColors();
        blockColors.method_12158(new BlockColorable() {
            public int method_12155(BlockState blockState, @Nullable EmptyBlockView blockView, @Nullable BlockPos blockPos, int i) {
                DoublePlantBlock.DoublePlantType doublePlantType = (DoublePlantBlock.DoublePlantType)blockState.get(DoublePlantBlock.VARIANT);
                return blockView == null || blockPos == null || doublePlantType != DoublePlantBlock.DoublePlantType.GRASS && doublePlantType != DoublePlantBlock.DoublePlantType.FERN ? -1 : BiomeColors.getGrassColor(blockView, blockPos);
            }
        }, Blocks.DOUBLE_PLANT);
        blockColors.method_12158(new BlockColorable() {
            public int method_12155(BlockState blockState, @Nullable EmptyBlockView blockView, @Nullable BlockPos blockPos, int i) {
                if (blockView != null && blockPos != null) {
                    BlockEntity blockEntity = blockView.getBlockEntity(blockPos);
                    if (blockEntity instanceof FlowerPotBlockEntity) {
                        Item item = ((FlowerPotBlockEntity)blockEntity).getItem();
                        if (item instanceof BlockItem) {
                            BlockState blockState2 = Block.getBlockFromItem(item).getDefaultState();
                            return blockColors.method_12157(blockState2, blockView, blockPos, i);
                        }
                    }

                    return -1;
                } else {
                    return -1;
                }
            }
        }, Blocks.FLOWER_POT);
        blockColors.method_12158(new BlockColorable() {
            public int method_12155(BlockState blockState, @Nullable EmptyBlockView blockView, @Nullable BlockPos blockPos, int i) {
                return blockView != null && blockPos != null ? BiomeColors.getGrassColor(blockView, blockPos) : GrassColors.getColor(0.5D, 1.0D);
            }
        }, Blocks.GRASS);
        blockColors.method_12158(new BlockColorable() {
            public int method_12155(BlockState blockState, @Nullable EmptyBlockView blockView, @Nullable BlockPos blockPos, int i) {
                PlanksBlock.WoodType woodType = (PlanksBlock.WoodType)blockState.get(Leaves1Block.VARIANT);
                if (woodType == PlanksBlock.WoodType.SPRUCE) {
                    return FoliageColors.getSpruceColor();
                } else if (woodType == PlanksBlock.WoodType.BIRCH) {
                    return FoliageColors.getBirchColor();
                } else {
                    return blockView != null && blockPos != null ? BiomeColors.getFoliageColor(blockView, blockPos) : FoliageColors.getDefaultColor();
                }
            }
        }, Blocks.LEAVES);
        blockColors.method_12158(new BlockColorable() {
            public int method_12155(BlockState blockState, @Nullable EmptyBlockView blockView, @Nullable BlockPos blockPos, int i) {
                return blockView != null && blockPos != null ? BiomeColors.getFoliageColor(blockView, blockPos) : FoliageColors.getDefaultColor();
            }
        }, Blocks.LEAVES2);
        blockColors.method_12158(new BlockColorable() {
            public int method_12155(BlockState blockState, @Nullable EmptyBlockView blockView, @Nullable BlockPos blockPos, int i) {
                return blockView != null && blockPos != null ? BiomeColors.getWaterColor(blockView, blockPos) : -1;
            }
        }, Blocks.WATER, Blocks.FLOWING_WATER);
        blockColors.method_12158(new BlockColorable() {
            public int method_12155(BlockState blockState, @Nullable EmptyBlockView blockView, @Nullable BlockPos blockPos, int i) {
                return RedstoneWireBlock.method_8877((Integer)blockState.get(RedstoneWireBlock.POWER));
            }
        }, Blocks.REDSTONE_WIRE);
        blockColors.method_12158(new BlockColorable() {
            public int method_12155(BlockState blockState, @Nullable EmptyBlockView blockView, @Nullable BlockPos blockPos, int i) {
                return blockView != null && blockPos != null ? BiomeColors.getGrassColor(blockView, blockPos) : -1;
            }
        }, Blocks.SUGARCANE);
        blockColors.method_12158(new BlockColorable() {
            public int method_12155(BlockState blockState, @Nullable EmptyBlockView blockView, @Nullable BlockPos blockPos, int i) {
                int j = (Integer)blockState.get(AttachedStemBlock.AGE);
                int k = j * 32;
                int l = 255 - j * 8;
                int m = j * 4;
                return k << 16 | l << 8 | m;
            }
        }, Blocks.MELON_STEM, Blocks.PUMPKIN_STEM);
        blockColors.method_12158(new BlockColorable() {
            public int method_12155(BlockState blockState, @Nullable EmptyBlockView blockView, @Nullable BlockPos blockPos, int i) {
                if (blockView != null && blockPos != null) {
                    return BiomeColors.getGrassColor(blockView, blockPos);
                } else {
                    return blockState.get(TallPlantBlock.TYPE) == TallPlantBlock.GrassType.DEAD_BUSH ? 16777215 : GrassColors.getColor(0.5D, 1.0D);
                }
            }
        }, Blocks.TALLGRASS);
        blockColors.method_12158(new BlockColorable() {
            public int method_12155(BlockState blockState, @Nullable EmptyBlockView blockView, @Nullable BlockPos blockPos, int i) {
                return blockView != null && blockPos != null ? BiomeColors.getFoliageColor(blockView, blockPos) : FoliageColors.getDefaultColor();
            }
        }, Blocks.VINE);
        blockColors.method_12158(new BlockColorable() {
            public int method_12155(BlockState blockState, @Nullable EmptyBlockView blockView, @Nullable BlockPos blockPos, int i) {
                return blockView != null && blockPos != null ? 2129968 : 7455580;
            }
        }, Blocks.LILY_PAD);
        return blockColors;
    }

    public int method_13410(BlockState blockState) {
        BlockColorable blockColorable = (BlockColorable)this.BlockColor2Id.fromId(Block.getIdByBlock(blockState.getBlock()));
        if (blockColorable != null) {
            return blockColorable.method_12155(blockState, (EmptyBlockView)null, (BlockPos)null, 0);
        } else {
            MaterialColor materialColor = blockState.getMaterialColor();
            return materialColor != null ? materialColor.color : -1;
        }
    }

    public int method_12157(BlockState blockState, @Nullable EmptyBlockView blockView, @Nullable BlockPos blockPos, int i) {
        BlockColorable blockColorable = (BlockColorable)this.BlockColor2Id.fromId(Block.getIdByBlock(blockState.getBlock()));
        return blockColorable == null ? -1 : blockColorable.method_12155(blockState, blockView, blockPos, i);
    }

    public void method_12158(BlockColorable blockColorable, Block... blocks) {
        Block[] blocks2 = blocks;
        int i = blocks.length;

        for(int j = 0; j < i; ++j) {
            Block block = blocks2[j];
            this.BlockColor2Id.set(blockColorable, Block.getIdByBlock(block));
        }

    }
}