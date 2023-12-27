package dev.u9g.minecraftdatagenerator.ClientSideAnnoyances;

import net.minecraft.util.math.MathHelper;

public class ServerSideRedstoneWireBlock {
    public static int getWireColor(int powerLevel) {
        float f = (float)powerLevel / 15.0F;
        float g = f * 0.6F + 0.4F;
        if (powerLevel == 0) {
            g = 0.3F;
        }

        float h = f * f * 0.7F - 0.5F;
        float j = f * f * 0.6F - 0.7F;
        if (h < 0.0F) {
            h = 0.0F;
        }

        if (j < 0.0F) {
            j = 0.0F;
        }

        int k = MathHelper.clamp((int)(g * 255.0F), 0, 255);
        int l = MathHelper.clamp((int)(h * 255.0F), 0, 255);
        int m = MathHelper.clamp((int)(j * 255.0F), 0, 255);
        return -16777216 | k << 16 | l << 8 | m;
    }
}
