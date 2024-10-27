package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.mixin.accessor.LanguageAccessor;
import dev.u9g.minecraftdatagenerator.util.Registries;

import java.util.Map;

public class LanguageDataGenerator implements IDataGenerator {
    @Override
    public String getDataName() {
        return "language";
    }

    @Override
    public JsonElement generateDataJson() {
        try {
            JsonObject obj = new JsonObject();
            Map<String, String> translations = ((LanguageAccessor) Registries.LANGUAGE).translations();
            for (Map.Entry<String, String> entry : translations.entrySet()) {
                obj.addProperty(entry.getKey(), entry.getValue());
            }
            return obj;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate language file", e);
        }
    }
}
