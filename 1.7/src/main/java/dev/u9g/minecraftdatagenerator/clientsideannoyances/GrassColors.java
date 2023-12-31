package dev.u9g.minecraftdatagenerator.clientsideannoyances;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;

public class GrassColors {
    private static int[] colorMap = new int[65536];

    public static void setColorMap(int[] map) {
        colorMap = map;
    }

    public static int getColor(double temperature, double humidity) {
        int n = (int) ((1.0 - temperature) * 255.0);
        int n2 = (int) ((1.0 - (humidity *= temperature)) * 255.0);
        return colorMap[n2 << 8 | n];
    }

    public static int getGrassColor(Biome biome) {
        double d = MathHelper.clamp(biome.temperature, 0.0f, 1.0f);
        double e = MathHelper.clamp(biome.downfall, 0.0f, 1.0f);
        return GrassColors.getColor(d, e);
    }
}



