package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class StopMixin {
    @Inject(method = "shutdown()V", at = @At("HEAD"), cancellable = true)
    private void init(CallbackInfo cir) {
        cir.cancel();
    }
}
