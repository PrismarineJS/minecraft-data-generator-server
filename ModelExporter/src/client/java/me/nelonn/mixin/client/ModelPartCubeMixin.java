package me.nelonn.mixin.client;

import me.nelonn.accessor.CubeAccessor;
import net.minecraft.client.model.geom.ModelPart;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(ModelPart.Cube.class)
public abstract class ModelPartCubeMixin implements CubeAccessor {

    @Unique private Vector4f tex = new Vector4f();
    @Unique private Vector3f grow = new Vector3f();

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(int i, int j, float f, float g, float h, float k, float l, float m, float n, float o, float p, boolean bl, float q, float r, Set set, CallbackInfo ci) {
        tex.x = i;
        tex.y = j;
        tex.z = q;
        tex.w = r;
        grow.x = n;
        grow.y = o;
        grow.z = p;
    }

    @Override
    public Vector4f modelExporter$texUVWH() {
        return tex;
    }

    @Override
    public Vector3f modelExporter$getGrow() {
        return grow;
    }
}
