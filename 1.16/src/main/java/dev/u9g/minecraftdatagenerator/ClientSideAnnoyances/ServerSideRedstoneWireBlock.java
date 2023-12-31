package dev.u9g.minecraftdatagenerator.ClientSideAnnoyances;

import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.MathHelper;

public class ServerSideRedstoneWireBlock {
    private static final Vector3f[] data = new Vector3f[16];

    static {
        for (int i = 0; i <= 15; ++i) {
            float f = 0;
            float g = f * 0.6f + ((f = (float) i / 15.0f) > 0.0f ? 0.4f : 0.3f);
            float h = MathHelper.clamp(f * f * 0.7f - 0.5f, 0.0f, 1.0f);
            float j = MathHelper.clamp(f * f * 0.6f - 0.7f, 0.0f, 1.0f);
            data[i] = new Vector3f(g, h, j);
        }
    }

    public static int getWireColor(int powerLevel) {
        Vector3f vector3f = data[powerLevel];
        return packRgb(vector3f.getX(), vector3f.getY(), vector3f.getZ());
    }

    private static int packRgb(float r, float g, float b) {
        return packRgb(MathHelper.floor(r * 255.0f), MathHelper.floor(g * 255.0f), MathHelper.floor(b * 255.0f));
    }

    private static int packRgb(int r, int g, int b) {
        int i = r;
        i = (i << 8) + g;
        i = (i << 8) + b;
        return i;
    }
}
