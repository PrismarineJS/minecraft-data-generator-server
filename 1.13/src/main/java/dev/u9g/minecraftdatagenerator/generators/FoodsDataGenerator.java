package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.item.FishItem;
import net.minecraft.item.FoodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Iterator;
import java.util.Objects;

public class FoodsDataGenerator implements IDataGenerator {

    @Override
    public String getDataName() {
        return "foods";
    }

    public JsonArray generateDataJson() {
        JsonArray resultsArray = new JsonArray();
        Registry<Item> itemRegistry = Registry.ITEM;
        for (Item item : (Iterable<Item>) itemRegistry) {
            if (item instanceof FoodItem) {
                resultsArray.add(generateFoodDescriptor(itemRegistry, (FoodItem)item));
            }
        }
        return resultsArray;
    }

    public static JsonObject generateFoodDescriptor(Registry<Item> registry, FoodItem foodItem) {
        JsonObject foodDesc = new JsonObject();
        Identifier registryKey = registry.getId(foodItem);

        foodDesc.addProperty("id", registry.getRawId(foodItem));
        foodDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());

        foodDesc.addProperty("stackSize", foodItem.getMaxCount());
        foodDesc.addProperty("displayName", DGU.translateText(foodItem.getTranslationKey()));
        float foodPoints = foodItem.getHungerPoints(getDefaultStack(foodItem));
        float saturationRatio = foodItem.getSaturation(getDefaultStack(foodItem)) * 2.0F;
        float saturation = foodPoints * saturationRatio;

        foodDesc.addProperty("foodPoints", foodPoints);
        foodDesc.addProperty("saturation", saturation);

        foodDesc.addProperty("effectiveQuality", foodPoints + saturation);
        foodDesc.addProperty("saturationRatio", saturationRatio);
        return foodDesc;
    }

    private static ItemStack getDefaultStack(FoodItem foodItem) {
        return new ItemStack(foodItem);
    }
}
