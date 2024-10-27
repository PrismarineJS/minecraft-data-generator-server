package dev.u9g.minecraftdatagenerator.generators;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.item.Item;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeDataGenerator implements IDataGenerator {

    private static int getRawIdFor(Item item) {
        return DGU.getWorld().getRegistryManager().getOrThrow(RegistryKeys.ITEM).getRawId(item);
    }

    @Override
    public String getDataName() {
        return "recipes";
    }

    @Override
    public JsonElement generateDataJson() {
        DynamicRegistryManager registryManager = DGU.getWorld().getRegistryManager();
        JsonObject finalObj = new JsonObject();
        Multimap<Integer, JsonObject> recipes = ArrayListMultimap.create();
        for (RecipeEntry<?> recipeE : Objects.requireNonNull(DGU.getWorld().getServer()).getRecipeManager().values()) {
            Recipe<?> recipe = recipeE.value();
            if (recipe instanceof ShapedRecipe sr) {
                var ingredients = sr.getIngredients();
                List<Integer> ingr = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    if (i >= ingredients.size()) {
                        ingr.add(null);
                        continue;
                    }
                    var stacks = ingredients.get(i).get();
                    var matching = stacks.getMatchingItems();
                    if (matching.isEmpty()) {
                        ingr.add(null);
                    } else {
                        ingr.add(getRawIdFor(matching.getFirst().value()));
                    }
                }
                //Lists.reverse(ingr);

                JsonArray inShape = new JsonArray();


                var iter = ingr.iterator();
                for (int y = 0; y < sr.getHeight(); y++) {
                    var jsonRow = new JsonArray();
                    for (int z = 0; z < sr.getWidth(); z++) {
                        jsonRow.add(iter.next());
                    }
                    inShape.add(jsonRow);
                }

                JsonObject finalRecipe = new JsonObject();
                finalRecipe.add("inShape", inShape);

                var resultObject = new JsonObject();
                resultObject.addProperty("id", getRawIdFor(sr.craft(CraftingRecipeInput.EMPTY, registryManager).getItem()));
                resultObject.addProperty("count", sr.craft(CraftingRecipeInput.EMPTY, registryManager).getCount());
                finalRecipe.add("result", resultObject);

                String id = ((Integer) getRawIdFor(sr.craft(CraftingRecipeInput.EMPTY, registryManager).getItem())).toString();

                if (!finalObj.has(id)) {
                    finalObj.add(id, new JsonArray());
                }
                finalObj.get(id).getAsJsonArray().add(finalRecipe);
//                var input = new JsonArray();
//                var ingredients = sr.getIngredients().stream().toList();
//                for (int y = 0; y < sr.getHeight(); y++) {
//                    var arr = new JsonArray();
//                    for (int x = 0; x < sr.getWidth(); x++) {
//                        if ((y*3)+x >= ingredients.size()) {
//                            arr.add(JsonNull.INSTANCE);
//                            continue;
//                        }
//                        var ingredient = ingredients.get((y*3)+x).getMatchingStacks(); // FIXME: fix when there are more than one matching stack
//                        if (ingredient.length == 0) {
//                            arr.add(JsonNull.INSTANCE);
//                        } else {
//                            arr.add(getRawIdFor(ingredient[0].getItem()));
//                        }
//                    }
//                    input.add(arr);
//                }
//                var rootRecipeObject = new JsonObject();
//                rootRecipeObject.add("inShape", input);
//                var resultObject = new JsonObject();
//                resultObject.addProperty("id", getRawIdFor(sr.getOutput().getItem()));
//                resultObject.addProperty("count", sr.getOutput().getCount());
//                rootRecipeObject.add("result", resultObject);
//                recipes.put(getRawIdFor(sr.getOutput().getItem()), rootRecipeObject);
            } else if (recipe instanceof ShapelessRecipe sl) {
                var ingredients = new JsonArray();
                for (Ingredient ingredient : sl.getIngredientPlacement().getIngredients()) {
                    if (ingredient.getMatchingItems().isEmpty()) continue;
                    ingredients.add(getRawIdFor(ingredient.getMatchingItems().getFirst().value()));
                }
                var rootRecipeObject = new JsonObject();
                rootRecipeObject.add("ingredients", ingredients);
                var resultObject = new JsonObject();
                resultObject.addProperty("id", getRawIdFor(sl.craft(CraftingRecipeInput.EMPTY, registryManager).getItem()));
                resultObject.addProperty("count", sl.craft(CraftingRecipeInput.EMPTY, registryManager).getCount());
                rootRecipeObject.add("result", resultObject);
                recipes.put(getRawIdFor(sl.craft(CraftingRecipeInput.EMPTY, registryManager).getItem()), rootRecipeObject);
            }
        }
        recipes.forEach((a, b) -> {
            if (!finalObj.has(a.toString())) {
                finalObj.add(a.toString(), new JsonArray());
            }
            finalObj.get(a.toString()).getAsJsonArray().add(b);
        });
        return finalObj;
    }
}
