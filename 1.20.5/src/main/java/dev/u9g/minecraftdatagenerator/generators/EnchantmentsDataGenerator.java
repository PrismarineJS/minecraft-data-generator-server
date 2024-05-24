package dev.u9g.minecraftdatagenerator.generators;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.List;

public class EnchantmentsDataGenerator implements IDataGenerator {

    private static final ImmutableMap<TagKey, String> ENCHANTMENT_TARGET_NAMES = ImmutableMap.<TagKey, String>builder()
            .put(ItemTags.ARMOR_ENCHANTABLE, "armor")
            .put(ItemTags.FOOT_ARMOR, "armor_feet")
            .put(ItemTags.LEG_ARMOR, "armor_legs")
            .put(ItemTags.CHEST_ARMOR, "armor_chest")
            .put(ItemTags.HEAD_ARMOR, "armor_head")
            .put(ItemTags.WEAPON_ENCHANTABLE, "weapon")
            .put(ItemTags.MINING_ENCHANTABLE, "digger")
            .put(ItemTags.FISHING_ENCHANTABLE, "fishing_rod")
            .put(ItemTags.TRIDENT_ENCHANTABLE, "trident")
            .put(ItemTags.DURABILITY_ENCHANTABLE, "breakable")
            .put(ItemTags.BOW_ENCHANTABLE, "bow")
            .put(ItemTags.EQUIPPABLE_ENCHANTABLE, "wearable")
            .put(ItemTags.CROSSBOW_ENCHANTABLE, "crossbow")
            .put(ItemTags.VANISHING_ENCHANTABLE, "vanishable")
            .build();

    public static String getEnchantmentTargetName(TagKey target) {
        return ENCHANTMENT_TARGET_NAMES.getOrDefault(target, target.toString().toLowerCase());
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
        Identifier registryKey = registry.getKey(enchantment).orElseThrow().getValue();

        enchantmentDesc.addProperty("id", registry.getRawId(enchantment));
        enchantmentDesc.addProperty("name", registryKey.getPath());
        enchantmentDesc.addProperty("displayName", DGU.translateText(enchantment.getTranslationKey()));

        enchantmentDesc.addProperty("maxLevel", enchantment.getMaxLevel());
        enchantmentDesc.add("minCost", generateEnchantmentMinPowerCoefficients(enchantment));
        enchantmentDesc.add("maxCost", generateEnchantmentMaxPowerCoefficients(enchantment));

        enchantmentDesc.addProperty("treasureOnly", enchantment.isTreasure());
        enchantmentDesc.addProperty("curse", enchantment.isCursed());

        List<Enchantment> incompatibleEnchantments = registry.stream()
                .filter(other -> !enchantment.canCombine(other))
                .filter(other -> other != enchantment)
                .toList();

        JsonArray excludes = new JsonArray();
        for (Enchantment excludedEnchantment : incompatibleEnchantments) {
            Identifier otherKey = registry.getKey(excludedEnchantment).orElseThrow().getValue();
            excludes.add(otherKey.getPath());
        }
        enchantmentDesc.add("exclude", excludes);

        enchantmentDesc.addProperty("category", getEnchantmentTargetName(enchantment.getApplicableItems()));
        enchantmentDesc.addProperty("weight", enchantment.isTreasure());
        enchantmentDesc.addProperty("tradeable", enchantment.isAvailableForEnchantedBookOffer());
        enchantmentDesc.addProperty("discoverable", enchantment.isAvailableForRandomSelection());

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
