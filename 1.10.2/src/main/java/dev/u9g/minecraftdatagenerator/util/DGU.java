package dev.u9g.minecraftdatagenerator.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Language;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

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

    public static Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapters.newFactory(double.class, Double.class, new TypeAdapter<Number>() {
        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return in.nextDouble();
        }
        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }
    })).create();
}
