package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import dev.u9g.minecraftdatagenerator.util.Registries;
import net.minecraft.item.FoodItem;
import net.minecraft.item.Item;

import java.util.Objects;

public class FoodsDataGenerator implements IDataGenerator {
    public static JsonObject generateFoodDescriptor(FoodItem foodItem) {
        JsonObject foodDesc = new JsonObject();
        String registryKey = Registries.ITEMS.getId(foodItem);

        foodDesc.addProperty("id", Registries.ITEMS.getRawId(foodItem));
        foodDesc.addProperty("name", Objects.requireNonNull(registryKey).replace("minecraft:", ""));

        foodDesc.addProperty("stackSize", foodItem.getMaxCount());
        foodDesc.addProperty("displayName", foodItem.getDisplayName(DGU.stackFor(foodItem)));
        float foodPoints = foodItem.getHungerPoints(DGU.stackFor(foodItem));
        float saturationRatio = foodItem.getSaturation(DGU.stackFor(foodItem)) * 2.0F;
        float saturation = foodPoints * saturationRatio;

        foodDesc.addProperty("foodPoints", foodPoints);
        foodDesc.addProperty("saturation", saturation);

        foodDesc.addProperty("effectiveQuality", foodPoints + saturation);
        foodDesc.addProperty("saturationRatio", saturationRatio);
        return foodDesc;
    }

    @Override
    public String getDataName() {
        return "foods";
    }

    public JsonArray generateDataJson() {
        JsonArray resultsArray = new JsonArray();
        for (Item item : Registries.ITEMS) {
            if (item instanceof FoodItem) {
                resultsArray.add(generateFoodDescriptor((FoodItem) item));
            }
        }
        return resultsArray;
    }
}
