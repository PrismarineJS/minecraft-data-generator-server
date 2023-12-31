package dev.u9g.minecraftdatagenerator.ClientSideAnnoyances;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.FlowerPotBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.util.collection.IdList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlockColors {
    private final IdList<BlockColorable> BlockColor2Id = new IdList<>(32);

    public BlockColors() {
    }

    public static BlockColors create() {
        final BlockColors blockColors = new BlockColors();
        blockColors.method_12158((blockState, blockView, blockPos, i) -> {
            DoublePlantBlock.DoublePlantType doublePlantType = blockState.get(DoublePlantBlock.VARIANT);
            return blockView != null && blockPos != null &&
                    (doublePlantType == DoublePlantBlock.DoublePlantType.GRASS || doublePlantType == DoublePlantBlock.DoublePlantType.FERN) ?
                    BiomeColors.getGrassColor(blockView, blockState.get(DoublePlantBlock.HALF) == DoublePlantBlock.HalfType.UPPER ? blockPos.down() : blockPos) : -1;
        }, Blocks.DOUBLE_PLANT);
        blockColors.method_12158((blockState, blockView, blockPos, i) -> {
            if (blockView != null && blockPos != null) {
                BlockEntity blockEntity = blockView.getBlockEntity(blockPos);
                if (blockEntity instanceof FlowerPotBlockEntity) {
                    Item item = ((FlowerPotBlockEntity) blockEntity).getItem();
                    BlockState blockState2 = Block.getBlockFromItem(item).getDefaultState();
                    return blockColors.method_12157(blockState2, blockView, blockPos, i);
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        }, Blocks.FLOWER_POT);
        blockColors.method_12158((blockState, blockView, blockPos, i) -> blockView != null && blockPos != null ? BiomeColors.getGrassColor(blockView, blockPos) : GrassColors.getColor(0.5D, 1.0D), Blocks.GRASS);
        blockColors.method_12158((blockState, blockView, blockPos, i) -> {
            PlanksBlock.WoodType woodType = blockState.get(Leaves1Block.VARIANT);
            if (woodType == PlanksBlock.WoodType.SPRUCE) {
                return FoliageColors.getSpruceColor();
            } else if (woodType == PlanksBlock.WoodType.BIRCH) {
                return FoliageColors.getBirchColor();
            } else {
                return blockView != null && blockPos != null ? BiomeColors.getFoliageColor(blockView, blockPos) : FoliageColors.getDefaultColor();
            }
        }, Blocks.LEAVES);
        blockColors.method_12158(new BlockColorable() {
            public int method_12155(BlockState blockState, @Nullable BlockView blockView, @Nullable BlockPos blockPos, int i) {
                return blockView != null && blockPos != null ? BiomeColors.getFoliageColor(blockView, blockPos) : FoliageColors.getDefaultColor();
            }
        }, Blocks.LEAVES2);
        blockColors.method_12158((blockState, blockView, blockPos, i) -> blockView != null && blockPos != null ? BiomeColors.getWaterColor(blockView, blockPos) : -1, Blocks.WATER, Blocks.FLOWING_WATER);
        blockColors.method_12158((blockState, blockView, blockPos, i) -> RedstoneWireBlock.method_8877((Integer) blockState.get(RedstoneWireBlock.POWER)), Blocks.REDSTONE_WIRE);
        blockColors.method_12158((blockState, blockView, blockPos, i) -> blockView != null && blockPos != null ? BiomeColors.getGrassColor(blockView, blockPos) : -1, Blocks.SUGARCANE);
        blockColors.method_12158((blockState, blockView, blockPos, i) -> {
            int j = blockState.get(AttachedStemBlock.AGE);
            int k = j * 32;
            int l = 255 - j * 8;
            int m = j * 4;
            return k << 16 | l << 8 | m;
        }, Blocks.MELON_STEM, Blocks.PUMPKIN_STEM);
//        blockColors.method_12158((blockState, blockView, blockPos, i) -> {
//            if (blockView != null && blockPos != null) {
//                return BiomeColors.getGrassColor(blockView, blockPos);
//            } else {
//                return blockState.get(TallPlantBlock.TYPE) == TallPlantBlock.GrassType.DEAD_BUSH ? 16777215 : GrassColors.getColor(0.5D, 1.0D);
//            }
//        }, Blocks.TALLGRASS);
        blockColors.method_12158((blockState, blockView, blockPos, i) -> blockView != null && blockPos != null ? BiomeColors.getGrassColor(blockView, blockPos) : blockState.get(TallPlantBlock.TYPE) == TallPlantBlock.GrassType.DEAD_BUSH ? 16777215 : GrassColors.getColor(0.5D, 1.0D), Blocks.TALLGRASS);
        blockColors.method_12158((blockState, blockView, blockPos, i) -> blockView != null && blockPos != null ? BiomeColors.getFoliageColor(blockView, blockPos) : FoliageColors.getDefaultColor(), Blocks.VINE);
        blockColors.method_12158((blockState, blockView, blockPos, i) -> blockView != null && blockPos != null ? 2129968 : 7455580, Blocks.LILY_PAD);
        return blockColors;
    }

    public int method_13410(BlockState blockState, World world, BlockPos blockPos) {
        BlockColorable blockColorable = BlockColor2Id.fromId(Block.getIdByBlock(blockState.getBlock()));
        if (blockColorable != null) {
            return blockColorable.method_12155(blockState, null, null, 0);
        } else {
            MaterialColor materialColor = blockState.getMaterialColor(world, blockPos);
            return materialColor != null ? materialColor.color : -1;
        }
    }

    public int method_12157(BlockState blockState, @Nullable BlockView blockView, @Nullable BlockPos blockPos, int i) {
        net.minecraft.client.BlockColorable blockColorable = (net.minecraft.client.BlockColorable) this.BlockColor2Id.fromId(Block.getIdByBlock(blockState.getBlock()));
        return blockColorable == null ? -1 : blockColorable.method_12155(blockState, blockView, blockPos, i);
    }

    public void method_12158(BlockColorable blockColorable, Block... blocks) {
        Block[] var3 = blocks;
        int var4 = blocks.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            Block block = var3[var5];
            this.BlockColor2Id.set(blockColorable, Block.getIdByBlock(block));
        }
    }
}
