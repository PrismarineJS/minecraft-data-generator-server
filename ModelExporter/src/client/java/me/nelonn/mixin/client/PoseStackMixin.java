package me.nelonn.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import me.nelonn.accessor.PoseAccessor;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Deque;

@Mixin(PoseStack.class)
public class PoseStackMixin {

    @Shadow @Final private Deque<PoseStack.Pose> poseStack;

    @Inject(method = "translate(FFF)V", at = @At("TAIL"))
    private void translate(float f, float g, float h, CallbackInfo ci) {
        PoseAccessor pose = (PoseAccessor) (Object) this.poseStack.getLast();
        pose.modelExporter$translation().add(f, g, h);
    }

    @Inject(method = "scale(FFF)V", at = @At("HEAD"))
    private void scale(float f, float g, float h, CallbackInfo ci) {
        PoseAccessor pose = (PoseAccessor) (Object) this.poseStack.getLast();
        pose.modelExporter$scale().add(f, g, h);
    }

    @Inject(method = "mulPose(Lorg/joml/Quaternionf;)V", at = @At("HEAD"))
    private void mulPose(Quaternionf quaternionf, CallbackInfo ci) {
        PoseAccessor pose = (PoseAccessor) (Object) this.poseStack.getLast();
        pose.modelExporter$rotation().mul(quaternionf);
    }

}
