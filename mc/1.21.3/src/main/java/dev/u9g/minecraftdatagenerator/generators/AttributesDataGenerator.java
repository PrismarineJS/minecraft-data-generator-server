package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.RegistryKeys;

import java.util.Objects;

public class AttributesDataGenerator implements IDataGenerator {
    @Override
    public String getDataName() {
        return "attributes";
    }

    @Override
    public JsonElement generateDataJson() {
        JsonArray arr = new JsonArray();
        var registry = DGU.getWorld().getRegistryManager().getOrThrow(RegistryKeys.ATTRIBUTE);
        for (EntityAttribute attribute : registry) {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", Objects.requireNonNull(registry.getId(attribute)).toShortTranslationKey());
            obj.addProperty("resource", Objects.requireNonNull(registry.getId(attribute)).toTranslationKey());
            obj.addProperty("min", ((ClampedEntityAttribute) attribute).getMinValue());
            obj.addProperty("max", ((ClampedEntityAttribute) attribute).getMaxValue());
            obj.addProperty("default", attribute.getDefaultValue());
            arr.add(obj);
        }
        return arr;
    }
}
