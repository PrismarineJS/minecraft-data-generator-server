package dev.u9g.minecraftdatagenerator.clientsideannoyances;

import net.minecraft.block.*;
import net.minecraft.class_2181;
import net.minecraft.class_3721;
import net.minecraft.util.collection.IdList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.RenderBlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BlockColors {
    private final IdList<BlockColorable> BlockColor2Id = new IdList<>(32);

    public BlockColors() {
    }

    public static BlockColors create() {
        BlockColors blockColors = new BlockColors();
        blockColors.method_12158((blockState, renderBlockView, blockPos, i) -> renderBlockView != null && blockPos != null ? BiomeColors.method_19681(renderBlockView, blockState.getProperty(class_3721.field_18472) == class_2181.field_9374 ? blockPos.down() : blockPos) : -1, Blocks.LARGE_FERN, Blocks.TALL_GRASS);
        blockColors.method_12158((blockState, renderBlockView, blockPos, i) -> renderBlockView != null && blockPos != null ? BiomeColors.method_19681(renderBlockView, blockPos) : GrassColors.getColor(0.5D, 1.0D), Blocks.GRASS_BLOCK, Blocks.FERN, Blocks.GRASS, Blocks.POTTED_FERN);
        blockColors.method_12158((blockState, renderBlockView, blockPos, i) -> net.minecraft.client.color.world.FoliageColors.getSpruceColor(), Blocks.SPRUCE_LEAVES);
        blockColors.method_12158((blockState, renderBlockView, blockPos, i) -> net.minecraft.client.color.world.FoliageColors.getBirchColor(), Blocks.BIRCH_LEAVES);
        blockColors.method_12158((blockState, renderBlockView, blockPos, i) -> renderBlockView != null && blockPos != null ? BiomeColors.method_19684(renderBlockView, blockPos) : FoliageColors.getDefaultColor(), Blocks.OAK_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.VINE);
        blockColors.method_12158((blockState, renderBlockView, blockPos, i) -> renderBlockView != null && blockPos != null ? BiomeColors.method_19686(renderBlockView, blockPos) : -1, Blocks.WATER, Blocks.BUBBLE_COLUMN, Blocks.CAULDRON);
        blockColors.method_12158((blockState, renderBlockView, blockPos, i) -> RedstoneWireBlock.method_8877(blockState.getProperty(RedstoneWireBlock.POWER)), Blocks.REDSTONE_WIRE);
        blockColors.method_12158((blockState, renderBlockView, blockPos, i) -> renderBlockView != null && blockPos != null ? BiomeColors.method_19681(renderBlockView, blockPos) : -1, Blocks.SUGAR_CANE);
        blockColors.method_12158((blockState, renderBlockView, blockPos, i) -> 14731036, Blocks.ATTACHED_MELON_STEM, Blocks.ATTACHED_PUMPKIN_STEM);
        blockColors.method_12158((blockState, renderBlockView, blockPos, i) -> {
            int j = blockState.getProperty(AttachedStemBlock.field_18518);
            int k = j * 32;
            int l = 255 - j * 8;
            int m = j * 4;
            return k << 16 | l << 8 | m;
        }, Blocks.MELON_STEM, Blocks.PUMPKIN_STEM);
        blockColors.method_12158((blockState, renderBlockView, blockPos, i) -> renderBlockView != null && blockPos != null ? 2129968 : 7455580, Blocks.LILY_PAD);
        return blockColors;
    }

    public int method_13410(BlockState blockState, World world, BlockPos blockPos) {
        BlockColorable blockColorable = this.BlockColor2Id.fromId(Registry.BLOCK.getRawId(blockState.getBlock()));
        if (blockColorable != null) {
            return blockColorable.getColor(blockState, null, null, 0);
        } else {
            MaterialColor materialColor = blockState.method_16892(world, blockPos);
            return materialColor != null ? materialColor.color : -1;
        }
    }

    public int method_18332(BlockState blockState, @Nullable RenderBlockView renderBlockView, @Nullable BlockPos blockPos, int i) {
        BlockColorable blockColorable = this.BlockColor2Id.fromId(Registry.BLOCK.getRawId(blockState.getBlock()));
        return blockColorable == null ? -1 : blockColorable.getColor(blockState, renderBlockView, blockPos, i);
    }

    public void method_12158(BlockColorable blockColorable, Block... blocks) {
        int var4 = blocks.length;

        for (Block block : blocks) {
            this.BlockColor2Id.set(blockColorable, Registry.BLOCK.getRawId(block));
        }

    }
}

