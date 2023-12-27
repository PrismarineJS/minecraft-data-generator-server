package dev.u9g.minecraftdatagenerator.ClientSideAnnoyances;

import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.MathHelper;

public class ServerSideRedstoneWireBlock {
    public static int getWireColor(int powerLevel) {
        float f = (float)powerLevel / 15.0f;
        float g = f * 0.6f + 0.4f;
        if (powerLevel == 0) {
            g = 0.3f;
        }
        float h = f * f * 0.7f - 0.5f;
        float j = f * f * 0.6f - 0.7f;
        if (h < 0.0f) {
            h = 0.0f;
        }
        if (j < 0.0f) {
            j = 0.0f;
        }
        int k = MathHelper.clamp((int)(g * 255.0f), 0, 255);
        int l = MathHelper.clamp((int)(h * 255.0f), 0, 255);
        int m = MathHelper.clamp((int)(j * 255.0f), 0, 255);
        return 0xFF000000 | k << 16 | l << 8 | m;
    }
}
