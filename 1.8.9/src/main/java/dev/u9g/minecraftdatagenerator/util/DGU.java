package dev.u9g.minecraftdatagenerator.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class DGU {
    public static MinecraftServer getCurrentlyRunningServer() {
        return (MinecraftServer) FabricLoader.getInstance().getGameInstance();
    }

    @Environment(EnvType.CLIENT)
    private static String translateTextClient(String translationKey) {
        return I18n.translate(translationKey);
    }

    private static String translateTextFallback(String translationKey) {
        try {
            return Registries.LANGUAGE.translate(translationKey);
        } catch (Exception ignored) {}
        throw new RuntimeException("Failed to translate: '" + translationKey + "'");
    }

    public static String translateText(String translationKey) {
        EnvType environmentType = FabricLoader.getInstance().getEnvironmentType();
        if (environmentType == EnvType.CLIENT) {
            return translateTextClient(translationKey);
        }
        return translateTextFallback(translationKey);
    }

    @NotNull
    public static World getWorld() {
        return getCurrentlyRunningServer().getWorld();
    }

    public static ItemStack stackFor(Item ic) {
        return new ItemStack(ic);
    }
}
