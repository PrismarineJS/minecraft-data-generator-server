package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.Identifier;
import net.minecraft.registry.entry.RegistryEntry;


import java.util.List;

public class EnchantmentsDataGenerator implements IDataGenerator {
    public static String getEnchantmentTargetName(RegistryEntryList<Item> target) {
        TagKey<Item> tagKey = target.getTagKey().orElseThrow();
        return tagKey.id().getPath().split("/")[1];
    }

    //Equation enchantment costs follow is a * level + b, so we can easily retrieve a and b by passing zero level
    private static JsonObject generateEnchantmentMinPowerCoefficients(Enchantment enchantment) {
        int b = enchantment.getMinPower(0);
        int a = enchantment.getMinPower(1) - b;

        JsonObject resultObject = new JsonObject();
        resultObject.addProperty("a", a);
        resultObject.addProperty("b", b);
        return resultObject;
    }

    private static JsonObject generateEnchantmentMaxPowerCoefficients(Enchantment enchantment) {
        int b = enchantment.getMaxPower(0);
        int a = enchantment.getMaxPower(1) - b;

        JsonObject resultObject = new JsonObject();
        resultObject.addProperty("a", a);
        resultObject.addProperty("b", b);
        return resultObject;
    }

    public static JsonObject generateEnchantment(Registry<Enchantment> registry, Enchantment enchantment) {
        JsonObject enchantmentDesc = new JsonObject();
        RegistryEntry<Enchantment> enchantmentEntry = registry.getEntry(enchantment);
        Identifier registryKey = registry.getKey(enchantment).orElseThrow().getValue();

        enchantmentDesc.addProperty("id", registry.getRawId(enchantment));
        enchantmentDesc.addProperty("name", registryKey.getPath());
        String displayName = Enchantment.getName(registry.getEntry(enchantment), 1).getString();
        displayName = displayName.replaceAll(" I$", "");
        enchantmentDesc.addProperty("displayName", displayName);

        enchantmentDesc.addProperty("maxLevel", enchantment.getMaxLevel());
        enchantmentDesc.add("minCost", generateEnchantmentMinPowerCoefficients(enchantment));
        enchantmentDesc.add("maxCost", generateEnchantmentMaxPowerCoefficients(enchantment));

        enchantmentDesc.addProperty("treasureOnly", enchantmentEntry.isIn(EnchantmentTags.TREASURE));

        enchantmentDesc.addProperty("curse", enchantmentEntry.isIn(EnchantmentTags.CURSE));

        List<Enchantment> incompatibleEnchantments = registry.stream()
                .filter(other -> !Enchantment.canBeCombined(enchantmentEntry, registry.getEntry(other)))
                .filter(other -> other != enchantment)
                .toList();

        JsonArray excludes = new JsonArray();
        for (Enchantment excludedEnchantment : incompatibleEnchantments) {
            Identifier otherKey = registry.getKey(excludedEnchantment).orElseThrow().getValue();
            excludes.add(otherKey.getPath());
        }
        enchantmentDesc.add("exclude", excludes);
        enchantmentDesc.addProperty("category", getEnchantmentTargetName(enchantment.getApplicableItems()));
        enchantmentDesc.addProperty("weight", enchantment.getWeight());
        enchantmentDesc.addProperty("tradeable", enchantmentEntry.isIn(EnchantmentTags.TRADEABLE));
        enchantmentDesc.addProperty("discoverable", enchantmentEntry.isIn(EnchantmentTags.ON_RANDOM_LOOT));

        return enchantmentDesc;
    }

    @Override
    public String getDataName() {
        return "enchantments";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultsArray = new JsonArray();
        Registry<Enchantment> enchantmentRegistry = DGU.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT);
        enchantmentRegistry.stream()
                .forEach(enchantment -> resultsArray.add(generateEnchantment(enchantmentRegistry, enchantment)));
        return resultsArray;
    }

    
}
