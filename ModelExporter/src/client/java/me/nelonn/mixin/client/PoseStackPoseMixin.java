package me.nelonn.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import me.nelonn.accessor.PoseAccessor;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PoseStack.Pose.class)
public abstract class PoseStackPoseMixin implements PoseAccessor {

    @Unique private Vector3f translation = new Vector3f();
    @Unique private Vector3f scale = new Vector3f(1, 1, 1);
    @Unique private Quaternionf rotation = new Quaternionf();

    @Inject(method = "<init>(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;)V", at = @At("TAIL"))
    private void onConstruct(PoseStack.Pose pose, CallbackInfo ci) {
        PoseAccessor poseAccessor = (PoseAccessor) (Object) pose;
        translation.set(poseAccessor.modelExporter$translation());
        scale.set(poseAccessor.modelExporter$scale());
        rotation.set(poseAccessor.modelExporter$rotation());
    }

    @Override
    public Vector3f modelExporter$translation() {
        return translation;
    }

    @Override
    public Vector3f modelExporter$scale() {
        return scale;
    }

    @Override
    public Quaternionf modelExporter$rotation() {
        return rotation;
    }

}
