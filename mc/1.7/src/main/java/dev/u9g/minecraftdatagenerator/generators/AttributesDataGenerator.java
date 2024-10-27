package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.mixin.accessor.LanguageAccessor;
import net.minecraft.util.Language;

import java.util.Map;

public class AttributesDataGenerator implements IDataGenerator {
    @Override
    public String getDataName() {
        return "attributes";
    }

    @Override
    public JsonElement generateDataJson() {
        Language lang = new Language();
        Map<String, String> translations = ((LanguageAccessor) lang).translations();
        JsonArray arr = new JsonArray();
        for (Map.Entry<String, String> translation : translations.entrySet()) {
            String key = translation.getKey();
            if (!key.startsWith("attribute.name.")) continue;
            JsonObject obj = new JsonObject();
            key = key.replace("attribute.name.", "");
            obj.addProperty("name", key.split("\\.")[1]);
            obj.addProperty("resource", key);
            arr.add(obj);
        }
        return arr;
    }
}
