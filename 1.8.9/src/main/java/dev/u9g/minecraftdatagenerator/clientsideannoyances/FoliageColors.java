package dev.u9g.minecraftdatagenerator.clientsideannoyances;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;

public class FoliageColors {
    private static int[] colorMap = new int[65536];

    public static void setColorMap(int[] pixels) {
        colorMap = pixels;
    }

    public static int getColor(double temperature, double humidity) {
        humidity *= temperature;
        int i = (int) ((1.0D - temperature) * 255.0D);
        int j = (int) ((1.0D - humidity) * 255.0D);
        return colorMap[j << 8 | i];
    }

    public static int getSpruceColor() {
        return 6396257;
    }

    public static int getBirchColor() {
        return 8431445;
    }

    public static int getDefaultColor() {
        return 4764952;
    }

    public static int getFoliageColor(Biome biome) {
        double d = MathHelper.clamp(biome.temperature, 0.0f, 1.0f);
        double e = MathHelper.clamp(biome.downfall, 0.0f, 1.0f);
        return FoliageColors.getColor(d, e);
    }
}

