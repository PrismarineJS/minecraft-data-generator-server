package dev.u9g.minecraftdatagenerator.clientsideannoyances;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RenderBlockView;
import net.minecraft.world.biome.Biome;

import java.util.Iterator;

public class BiomeColors {
    private static final ColorProvider field_21146 = Biome::getGrassColor;
    private static final ColorProvider field_21147 = Biome::getFoliageColor;
    private static final ColorProvider field_21148 = (biome, blockPos) -> biome.getWaterColor();
    private static final ColorProvider field_21149 = (biome, blockPos) -> biome.method_16447();

    private static int method_19682(RenderBlockView renderBlockView, BlockPos blockPos, ColorProvider colorProvider) {
        int i = 0;
        int j = 0;
        int k = 0;
        int l = MinecraftClient.getInstance().options.field_19979;
        int m = (l * 2 + 1) * (l * 2 + 1);

        int n;
        for (Iterator<BlockPos.Mutable> var8 = BlockPos.mutableIterate(blockPos.getX() - l, blockPos.getY(), blockPos.getZ() - l, blockPos.getX() + l, blockPos.getY(), blockPos.getZ() + l).iterator(); var8.hasNext(); k += n & 255) {
            BlockPos.Mutable mutable = var8.next();
            n = colorProvider.getColor(renderBlockView.method_8577(mutable), mutable);
            i += (n & 16711680) >> 16;
            j += (n & '\uff00') >> 8;
        }

        return (i / m & 255) << 16 | (j / m & 255) << 8 | k / m & 255;
    }

    public static int method_19681(RenderBlockView renderBlockView, BlockPos blockPos) {
        return method_19682(renderBlockView, blockPos, field_21146);
    }

    public static int method_19684(RenderBlockView renderBlockView, BlockPos blockPos) {
        return method_19682(renderBlockView, blockPos, field_21147);
    }

    public static int method_19686(RenderBlockView renderBlockView, BlockPos blockPos) {
        return method_19682(renderBlockView, blockPos, field_21148);
    }

    interface ColorProvider {
        int getColor(Biome biome, BlockPos blockPos);
    }
}
