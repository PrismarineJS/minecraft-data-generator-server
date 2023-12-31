package dev.u9g.minecraftdatagenerator.mixin;

import dev.u9g.minecraftdatagenerator.Main;
import dev.u9g.minecraftdatagenerator.generators.DataGenerators;
import dev.u9g.minecraftdatagenerator.util.DGU;
import dev.u9g.minecraftdatagenerator.util.Registries;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Level;

@Mixin(MinecraftDedicatedServer.class)
public class ReadyMixin {
    @Inject(method = "setupServer()Z", at = @At("TAIL"))
    private void init(CallbackInfoReturnable<Boolean> cir) {
        Registries.init();
        Main.LOGGER.log(Level.INFO, "Starting data generation!");
        String versionName = DGU.getCurrentlyRunningServer().getVersion();
        Path serverRootDirectory = (new File(".")).toPath().toAbsolutePath();
        Path dataDumpDirectory = serverRootDirectory.resolve("minecraft-data").resolve(versionName);
        DataGenerators.runDataGenerators(dataDumpDirectory);
        Main.LOGGER.log(Level.INFO, "Done data generation!");
        System.exit(0);
    }
}
