package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.mixin.ClampedEntityAttributeAccessor;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.registry.Registry;

public class AttributesDataGenerator implements IDataGenerator {
    @Override
    public String getDataName() {
        return "attributes";
    }

    @Override
    public JsonElement generateDataJson() {
        JsonArray arr = new JsonArray();
        for (EntityAttribute attribute : Registry.ATTRIBUTE) {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", Registry.ATTRIBUTE.getId(attribute).getPath().split("\\.")[1]);
            obj.addProperty("resource", Registry.ATTRIBUTE.getId(attribute).getPath());
            obj.addProperty("min", ((ClampedEntityAttributeAccessor) attribute).getMinValue());
            obj.addProperty("max", ((ClampedEntityAttributeAccessor) attribute).getMaxValue());
            obj.addProperty("default", attribute.getDefaultValue());
            arr.add(obj);
        }
        return arr;
    }
}
