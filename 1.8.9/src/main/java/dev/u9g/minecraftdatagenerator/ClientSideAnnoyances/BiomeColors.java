package dev.u9g.minecraftdatagenerator.ClientSideAnnoyances;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.biome.Biome;

import java.util.Iterator;

public class BiomeColors {
    private static final ColorProvider GRASS_COLOR = new ColorProvider() {
        public int getColorAtPos(Biome biome, BlockPos pos) {
            return biome.getGrassColor(pos);
        }
    };
    private static final ColorProvider FOLIAGE_COLOR = new ColorProvider() {
        public int getColorAtPos(Biome biome, BlockPos pos) {
            return biome.getFoliageColor(pos);
        }
    };
    private static final ColorProvider WATER_COLOR = new ColorProvider() {
        public int getColorAtPos(Biome biome, BlockPos pos) {
            return biome.waterColor;
        }
    };

    private static int getColor(BlockView view, BlockPos pos, ColorProvider provider) {
        int i = 0;
        int j = 0;
        int k = 0;

        int l;
        for (Iterator iterator = BlockPos.mutableIterate(pos.add(-1, 0, -1), pos.add(1, 0, 1)).iterator(); iterator.hasNext(); k += l & 255) {
            BlockPos.Mutable mutable = (BlockPos.Mutable) iterator.next();
            l = provider.getColorAtPos(view.getBiome(mutable), mutable);
            i += (l & 16711680) >> 16;
            j += (l & '\uff00') >> 8;
        }

        return (i / 9 & 255) << 16 | (j / 9 & 255) << 8 | k / 9 & 255;
    }

    public static int getGrassColor(BlockView view, BlockPos pos) {
        return getColor(view, pos, GRASS_COLOR);
    }

    public static int getFoliageColor(BlockView view, BlockPos pos) {
        return getColor(view, pos, FOLIAGE_COLOR);
    }

    public static int getWaterColor(BlockView view, BlockPos pos) {
        return getColor(view, pos, WATER_COLOR);
    }

    interface ColorProvider {
        int getColorAtPos(Biome biome, BlockPos pos);
    }
}

