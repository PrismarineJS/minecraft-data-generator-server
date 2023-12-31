package dev.u9g.minecraftdatagenerator.ClientSideAnnoyances;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;

public class SkyColor {
    public static int getSkyColor(Biome biome) {
        return realSkyColor(biome.getTemperature());
    }

    private static int realSkyColor(float temperature) {
        temperature /= 3.0F;
        temperature = MathHelper.clamp(temperature, -1.0F, 1.0F);
        return hsvToRgb(0.62222224F - temperature * 0.05F, 0.5F + temperature * 0.1F, 1.0F);
    }

    public static int hsvToRgb(float hue, float saturation, float value) {
        int i = (int) (hue * 6.0F) % 6;
        float f = hue * 6.0F - (float) i;
        float g = value * (1.0F - saturation);
        float h = value * (1.0F - f * saturation);
        float j = value * (1.0F - (1.0F - f) * saturation);
        float k;
        float l;
        float m;
        switch (i) {
            case 0:
                k = value;
                l = j;
                m = g;
                break;
            case 1:
                k = h;
                l = value;
                m = g;
                break;
            case 2:
                k = g;
                l = value;
                m = j;
                break;
            case 3:
                k = g;
                l = h;
                m = value;
                break;
            case 4:
                k = j;
                l = g;
                m = value;
                break;
            case 5:
                k = value;
                l = g;
                m = h;
                break;
            default:
                throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
        }

        int n = clamp((int) (k * 255.0F), 0, 255);
        int o = clamp((int) (l * 255.0F), 0, 255);
        int p = clamp((int) (m * 255.0F), 0, 255);
        return n << 16 | o << 8 | p;
    }

    public static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        } else {
            return Math.min(value, max);
        }
    }
}
