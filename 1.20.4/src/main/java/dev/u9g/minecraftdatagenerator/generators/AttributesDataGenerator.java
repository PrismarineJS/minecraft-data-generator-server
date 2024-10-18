package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.RegistryKeys;

public class AttributesDataGenerator implements IDataGenerator {
    @Override
    public String getDataName() {
        return "attributes";
    }

    @Override
    public JsonElement generateDataJson() {
        JsonArray arr = new JsonArray();
        for (EntityAttribute attribute : DGU.getWorld().getRegistryManager().get(RegistryKeys.ATTRIBUTE)) {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", DGU.translateText(attribute.getTranslationKey()));
            obj.addProperty("resource", DGU.getWorld().getRegistryManager().get(RegistryKeys.ATTRIBUTE).getId(attribute).getPath());
            obj.addProperty("defaultValue", attribute.getDefaultValue());
            obj.addProperty("tracked", attribute.isTracked());
            arr.add(obj);
        }
        return arr;
    }
}
