package dev.u9g.minecraftdatagenerator.util;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Language;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class DGU {
    @SuppressWarnings("deprecation")
    public static MinecraftServer getCurrentlyRunningServer() {
        return (MinecraftServer) FabricLoader.getInstance().getGameInstance();
    }

    public static String translateText(String translationKey) {
        return Language.getInstance().get(translationKey);
    }

    @NotNull
    public static World getWorld() {
        return getCurrentlyRunningServer().getOverworld();
    }
}
