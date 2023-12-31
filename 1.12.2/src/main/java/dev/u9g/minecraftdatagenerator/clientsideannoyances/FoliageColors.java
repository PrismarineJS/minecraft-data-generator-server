package dev.u9g.minecraftdatagenerator.clientsideannoyances;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;

public class FoliageColors {
    private static int[] colorMap = new int[65536];

    public static void setColorMap(int[] pixels) {
        colorMap = pixels;
    }

    private static int getColor(double temperature, double humidity) {
        int i = (int) ((1.0 - temperature) * 255.0);
        int j = (int) ((1.0 - (humidity *= temperature)) * 255.0);
        return colorMap[j << 8 | i];
    }

    public static int getSpruceColor() {
        return 0x619961;
    }

    public static int getBirchColor() {
        return 8431445;
    }

    public static int getDefaultColor() {
        return 4764952;
    }

    public static int getFoliageColor(Biome biome) {
        double d = MathHelper.clamp(biome.getTemperature(), 0.0f, 1.0f);
        double e = MathHelper.clamp(biome.getRainfall(), 0.0f, 1.0f);
        return getColor(d, e);
    }
}
