package dev.u9g.minecraftdatagenerator.ClientSideAnnoyances;

import net.minecraft.block.*;
import net.minecraft.world.biome.Biome;


public class BiomeBlockColors {
    // generated manually from each Biome's Biome#getBlockColor
    public static int getBlockColor(Block block, Biome biome, int blockData) {
        if (block instanceof LilyPadBlock) {
            return 2129968;
        } else if (block instanceof AbstractFluidBlock && block.getMaterial() == Material.WATER) {
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    int n4 = biome.waterColor;
                    n += (n4 & 0xFF0000) >> 16;
                    n2 += (n4 & 0xFF00) >> 8;
                    n3 += n4 & 0xFF;
                }
            }
            return (n / 9 & 0xFF) << 16 | (n2 / 9 & 0xFF) << 8 | n3 / 9 & 0xFF;
        } else if (block instanceof AttachedStemBlock) {
            int n = blockData * 32;
            int n2 = 255 - blockData * 8;
            int n3 = blockData * 4;
            return n << 16 | n2 << 8 | n3;
        } else if (block instanceof RedstoneWireBlock) {
            return 0x800000;
        } else if (block instanceof DoublePlantBlock) {
            int n = ((DoublePlantBlock) block).method_6478(null, 0, 0, 0);
            if (n == 2 || n == 3) {
                return GrassColors.getGrassColor(biome);
            }
        } else if (block instanceof GrassBlock) {
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    int n4 = GrassColors.getGrassColor(biome);
                    n += (n4 & 0xFF0000) >> 16;
                    n2 += (n4 & 0xFF00) >> 8;
                    n3 += n4 & 0xFF;
                }
            }
            return (n / 9 & 0xFF) << 16 | (n2 / 9 & 0xFF) << 8 | n3 / 9 & 0xFF;
        } else if (block instanceof Leaves1Block) {
            if ((blockData & 3) == 1) {
                return FoliageColors.getSpruceColor();
            }
            if ((blockData & 3) == 2) {
                return FoliageColors.getBirchColor();
            }
        } else if (block instanceof LeavesBlock) {
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    int n4 = FoliageColors.getColor(biome);
                    n += (n4 & 0xFF0000) >> 16;
                    n2 += (n4 & 0xFF00) >> 8;
                    n3 += n4 & 0xFF;
                }
            }
            return (n / 9 & 0xFF) << 16 | (n2 / 9 & 0xFF) << 8 | n3 / 9 & 0xFF;
        } else if (block instanceof SugarCaneBlock) {
            return GrassColors.getGrassColor(biome);
        } else if (block instanceof TallPlantBlock & blockData != 0) {
            return GrassColors.getGrassColor(biome);
        } else if (block instanceof VineBlock) {
            return FoliageColors.getColor(biome);
        }

        return 0xFFFFFF;
    }
}
