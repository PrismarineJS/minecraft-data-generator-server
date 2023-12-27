package dev.u9g.minecraftdatagenerator.util;

import com.google.gson.*;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Language;
import net.minecraft.world.World;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

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
    private static Language language = Language.getInstance();

    private static String translateTextFallback(String translationKey) {
        try {
            return language.get(translationKey);
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

    public static World getWorld() {
        return getCurrentlyRunningServer().getOverworld();
    }

    public static final TypeAdapter<JsonElement> JSON_ELEMENT = new TypeAdapter<JsonElement>() {
        @Override public JsonElement read(JsonReader in) throws IOException {
            switch (in.peek()) {
                case STRING:
                    return new JsonPrimitive(in.nextString());
                case NUMBER:
                    String number = in.nextString();
                    return new JsonPrimitive(new LazilyParsedNumber(number));
                case BOOLEAN:
                    return new JsonPrimitive(in.nextBoolean());
                case NULL:
                    in.nextNull();
                    return JsonNull.INSTANCE;
                case BEGIN_ARRAY:
                    JsonArray array = new JsonArray();
                    in.beginArray();
                    while (in.hasNext()) {
                        array.add(read(in));
                    }
                    in.endArray();
                    return array;
                case BEGIN_OBJECT:
                    JsonObject object = new JsonObject();
                    in.beginObject();
                    while (in.hasNext()) {
                        object.add(in.nextName(), read(in));
                    }
                    in.endObject();
                    return object;
                case END_DOCUMENT:
                case NAME:
                case END_OBJECT:
                case END_ARRAY:
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override public void write(JsonWriter out, JsonElement value) throws IOException {
            if (value == null || value.isJsonNull()) {
                out.nullValue();
            } else if (value.isJsonPrimitive()) {
                JsonPrimitive primitive = value.getAsJsonPrimitive();
                if (primitive.isNumber()) {
                    if (primitive.getAsDouble() == primitive.getAsLong()) {
                        out.value(primitive.getAsLong());
                    } else {
                        out.value(primitive.getAsNumber());
                    }
                } else if (primitive.isBoolean()) {
                    out.value(primitive.getAsBoolean());
                } else {
                    out.value(primitive.getAsString());
                }

            } else if (value.isJsonArray()) {
                out.beginArray();
                for (JsonElement e : value.getAsJsonArray()) {
                    write(out, e);
                }
                out.endArray();

            } else if (value.isJsonObject()) {
                out.beginObject();
                for (Map.Entry<String, JsonElement> e : value.getAsJsonObject().entrySet()) {
                    out.name(e.getKey());
                    write(out, e.getValue());
                }
                out.endObject();

            } else {
                throw new IllegalArgumentException("Couldn't write " + value.getClass());
            }
        }
    };

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(JsonElement.class, JSON_ELEMENT).setPrettyPrinting().create();
}
