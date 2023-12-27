package dev.u9g.minecraftdatagenerator.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Itemable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Language;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;

public class DGU {

    @Environment(EnvType.CLIENT)
    private static MinecraftServer getCurrentlyRunningServerClient() {
        return MinecraftClient.getInstance().getServer();
    }

    @SuppressWarnings("deprecation")
    private static MinecraftServer getCurrentlyRunningServerDedicated() {
        return (MinecraftServer) FabricLoader.getInstance().getGameInstance();
    }

    public static MinecraftServer getCurrentlyRunningServer() {
        EnvType environmentType = FabricLoader.getInstance().getEnvironmentType();
        if (environmentType == EnvType.CLIENT) {
            return getCurrentlyRunningServerClient();
        } else if (environmentType == EnvType.SERVER) {
            return getCurrentlyRunningServerDedicated();
        }
        throw new UnsupportedOperationException();
    }

    @Environment(EnvType.CLIENT)
    private static String translateTextClient(String translationKey) {
        return I18n.translate(translationKey);
    }
    private static final Language language = Language.getInstance();

    private static String translateTextFallback(String translationKey) {
        try {
            return language.translate(translationKey);
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
        return getCurrentlyRunningServer().method_20312(DimensionType.OVERWORLD);
    }

    public static ItemStack stackFor(Itemable ic) {
        return new ItemStack(ic);
    }
}
