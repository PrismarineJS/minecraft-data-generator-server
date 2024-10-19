package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
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
        var registry = DGU.getWorld().getRegistryManager().get(RegistryKeys.ATTRIBUTE);
        for (EntityAttribute attribute : registry) {
            JsonObject obj = new JsonObject();
            obj.addProperty("name", registry.getId(attribute).getPath().split("\\.")[1]);
            obj.addProperty("resource", registry.getId(attribute).getPath());
            obj.addProperty("min", ((ClampedEntityAttribute) attribute).getMinValue());
            obj.addProperty("max", ((ClampedEntityAttribute) attribute).getMaxValue());
            obj.addProperty("default", attribute.getDefaultValue());
            arr.add(obj);
        }
        return arr;
    }
}
