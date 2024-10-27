package dev.u9g.minecraftdatagenerator.util;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Itemable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Language;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;

public class DGU {
    @SuppressWarnings("deprecation")
    public static MinecraftServer getCurrentlyRunningServer() {
        return (MinecraftServer) FabricLoader.getInstance().getGameInstance();
    }

    public static String translateText(String translationKey) {
        return Language.getInstance().translate(translationKey);
    }

    @NotNull
    public static World getWorld() {
        return getCurrentlyRunningServer().method_20312(DimensionType.OVERWORLD);
    }

    public static ItemStack stackFor(Itemable ic) {
        return new ItemStack(ic);
    }
}
