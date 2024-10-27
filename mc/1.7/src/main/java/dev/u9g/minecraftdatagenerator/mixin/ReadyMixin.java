package dev.u9g.minecraftdatagenerator.mixin;

import dev.u9g.minecraftdatagenerator.MinecraftDataGenerator;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;

@Mixin(MinecraftDedicatedServer.class)
public class ReadyMixin {
    @Inject(method = "setupServer()Z", at = @At("HEAD"))
    private void constructor(CallbackInfoReturnable<Boolean> cir) {
        ((MinecraftDedicatedServer) (Object) this).setServerPort(0);
    }

    @Inject(method = "setupServer()Z", at = @At("TAIL"))
    private void init(CallbackInfoReturnable<Boolean> cir) {
        System.setProperty("fabric.development", "false");
        MinecraftDataGenerator.start(
                DGU.getCurrentlyRunningServer().getVersion(),
                (new File(".")).toPath()
        );
    }
}
