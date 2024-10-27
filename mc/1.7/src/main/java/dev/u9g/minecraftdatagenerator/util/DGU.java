package dev.u9g.minecraftdatagenerator.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class DGU {
    public static MinecraftServer getCurrentlyRunningServer() {
        return MinecraftServer.getServer();
    }

    public static String translateText(String translationKey) {
        return Registries.LANGUAGE.translate(translationKey);
    }

    @NotNull
    public static World getWorld() {
        return getCurrentlyRunningServer().getWorld();
    }

    public static ItemStack stackFor(Item ic) {
        return new ItemStack(ic);
    }
}
