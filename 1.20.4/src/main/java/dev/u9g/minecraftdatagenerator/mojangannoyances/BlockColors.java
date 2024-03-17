package dev.u9g.minecraftdatagenerator.mojangannoyances;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.state.property.Property;
import net.minecraft.util.collection.IdList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public class BlockColors {
    private static final int NO_COLOR = -1;
    private final IdList<BlockColorProvider> providers = new IdList<>(32);
    private final Map<Block, Set<Property<?>>> properties = Maps.newHashMap();

    public BlockColors() {
    }

    public static BlockColors create() {
        BlockColors blockColors = new BlockColors();
        blockColors.registerColorProvider((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, state.get(TallPlantBlock.HALF) == DoubleBlockHalf.UPPER ? pos.down() : pos) : -1, Blocks.LARGE_FERN, Blocks.TALL_GRASS);
        blockColors.registerColorProperty(TallPlantBlock.HALF, Blocks.LARGE_FERN, Blocks.TALL_GRASS);
        blockColors.registerColorProvider((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0.5, 1.0), Blocks.GRASS_BLOCK, Blocks.FERN, Blocks.SHORT_GRASS, Blocks.POTTED_FERN);
        blockColors.registerColorProvider((state, world, pos, tintIndex) -> FoliageColors.getSpruceColor(), Blocks.SPRUCE_LEAVES);
        blockColors.registerColorProvider((state, world, pos, tintIndex) -> FoliageColors.getBirchColor(), Blocks.BIRCH_LEAVES);
        blockColors.registerColorProvider((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(), Blocks.OAK_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.VINE, Blocks.MANGROVE_LEAVES);
        blockColors.registerColorProvider((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getWaterColor(world, pos) : -1, Blocks.WATER, Blocks.BUBBLE_COLUMN, Blocks.WATER_CAULDRON);
        blockColors.registerColorProvider((state, world, pos, tintIndex) -> RedstoneWireBlock.getWireColor(state.get(RedstoneWireBlock.POWER)), Blocks.REDSTONE_WIRE);
        blockColors.registerColorProperty(RedstoneWireBlock.POWER, Blocks.REDSTONE_WIRE);
        blockColors.registerColorProvider((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : -1, Blocks.SUGAR_CANE);
        blockColors.registerColorProvider((state, world, pos, tintIndex) -> 14731036, Blocks.ATTACHED_MELON_STEM, Blocks.ATTACHED_PUMPKIN_STEM);
        blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
            int i = state.get(StemBlock.AGE);
            int j = i * 32;
            int k = 255 - i * 8;
            int l = i * 4;
            return j << 16 | k << 8 | l;
        }, Blocks.MELON_STEM, Blocks.PUMPKIN_STEM);
        blockColors.registerColorProperty(StemBlock.AGE, Blocks.MELON_STEM, Blocks.PUMPKIN_STEM);
        blockColors.registerColorProvider((state, world, pos, tintIndex) -> world != null && pos != null ? 2129968 : 7455580, Blocks.LILY_PAD);
        return blockColors;
    }

    public int getParticleColor(BlockState state, World world, BlockPos pos) {
        BlockColorProvider blockColorProvider = this.providers.get(DGU.getWorld().getRegistryManager().get(RegistryKeys.BLOCK).getRawId(state.getBlock()));
        if (blockColorProvider != null) {
            return blockColorProvider.getColor(state, null, null, 0);
        } else {
            MapColor mapColor = state.getMapColor(world, pos);
            return mapColor != null ? mapColor.color : -1;
        }
    }

    public int getColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {
        BlockColorProvider blockColorProvider = this.providers.get(DGU.getWorld().getRegistryManager().get(RegistryKeys.BLOCK).getRawId(state.getBlock()));
        return blockColorProvider == null ? -1 : blockColorProvider.getColor(state, world, pos, tintIndex);
    }

    public void registerColorProvider(BlockColorProvider provider, Block... blocks) {
        int var4 = blocks.length;

        for (Block block : blocks) {
            this.providers.set(provider, DGU.getWorld().getRegistryManager().get(RegistryKeys.BLOCK).getRawId(block));
        }

    }

    private void registerColorProperties(Set<Property<?>> properties, Block... blocks) {
        int var4 = blocks.length;

        for (Block block : blocks) {
            this.properties.put(block, properties);
        }

    }

    private void registerColorProperty(Property<?> property, Block... blocks) {
        this.registerColorProperties(ImmutableSet.of(property), blocks);
    }

    public Set<Property<?>> getProperties(Block block) {
        return this.properties.getOrDefault(block, ImmutableSet.of());
    }
}
