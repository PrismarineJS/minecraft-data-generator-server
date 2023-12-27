package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.mixin.LanguageAccessor;
import dev.u9g.minecraftdatagenerator.util.Registries;
import net.minecraft.util.Language;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public class LanguageDataGenerator implements IDataGenerator {
    @Override
    public String getDataName() {
        return "language";
    }

    @Override
    public JsonElement generateDataJson() {
        try {
            JsonObject obj = new JsonObject();
            Map<String, String> translations = ((LanguageAccessor)Registries.LANGUAGE).translations();
            for (Map.Entry<String, String> entry : translations.entrySet()) {
                obj.addProperty(entry.getKey(), entry.getValue());
            }
            return obj;
        } catch (Exception ignored) {}
        throw new RuntimeException("Failed to generate language file");
    }
}
