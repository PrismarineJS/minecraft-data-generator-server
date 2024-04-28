package dev.u9g.minecraftdatagenerator.clientsideannoyances;

import dev.u9g.minecraftdatagenerator.util.EmptyBlockView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;

import java.util.Iterator;

public class BiomeColors {
    private static final ColorProvider GRASS_COLOR = (biome, pos) -> {
        double d = MathHelper.clamp(biome.getTemperature(pos), 0.0F, 1.0F);
        double e = MathHelper.clamp(biome.getRainfall(), 0.0F, 1.0F);
        return GrassColors.getColor(d, e);
    };
    private static final ColorProvider FOLIAGE_COLOR = (biome, pos) -> {
        double d = MathHelper.clamp(biome.getTemperature(pos), 0.0F, 1.0F);
        double e = MathHelper.clamp(biome.getRainfall(), 0.0F, 1.0F);
        return FoliageColors.getColor(d, e);
    };
    private static final ColorProvider WATER_COLOR = (biome, pos) -> biome.getWaterColor();

    private static int getColor(EmptyBlockView view, BlockPos pos, ColorProvider provider) {
        int i = 0;
        int j = 0;
        int k = 0;

        int l;
        for (Iterator<BlockPos.Mutable> var6 = BlockPos.mutableIterate(pos.add(-1, 0, -1), pos.add(1, 0, 1)).iterator(); var6.hasNext(); k += l & 255) {
            BlockPos.Mutable mutable = var6.next();
            l = provider.getColorAtPos(view.getBiome(mutable), mutable);
            i += (l & 16711680) >> 16;
            j += (l & '\uff00') >> 8;
        }

        return (i / 9 & 255) << 16 | (j / 9 & 255) << 8 | k / 9 & 255;
    }

    public static int getGrassColor(EmptyBlockView view, BlockPos pos) {
        return getColor(view, pos, GRASS_COLOR);
    }

    public static int getFoliageColor(EmptyBlockView view, BlockPos pos) {
        return getColor(view, pos, FOLIAGE_COLOR);
    }

    public static int getWaterColor(EmptyBlockView view, BlockPos pos) {
        return getColor(view, pos, WATER_COLOR);
    }

    interface ColorProvider {
        int getColorAtPos(Biome biome, BlockPos pos);
    }
}

