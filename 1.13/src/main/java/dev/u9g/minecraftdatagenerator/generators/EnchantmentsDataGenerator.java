package dev.u9g.minecraftdatagenerator.generators;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.enchantment.*;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.*;
import java.util.stream.Collectors;

public class EnchantmentsDataGenerator implements IDataGenerator {

    private static final ImmutableMap<EnchantmentTarget, String> ENCHANTMENT_TARGET_NAMES = ImmutableMap.<EnchantmentTarget, String>builder()
            .put(EnchantmentTarget.ALL_ARMOR, "armor")
            .put(EnchantmentTarget.FEET, "armor_feet")
            .put(EnchantmentTarget.LEGS, "armor_legs")
            .put(EnchantmentTarget.ARMOR_CHEST, "armor_chest")
            .put(EnchantmentTarget.HEAD, "armor_head")
            .put(EnchantmentTarget.WEAPON, "weapon")
            .put(EnchantmentTarget.DIGGER, "digger")
            .put(EnchantmentTarget.FISHING_ROD, "fishing_rod")
            .put(EnchantmentTarget.TRIDENT, "trident")
            .put(EnchantmentTarget.BREAKABLE, "breakable")
            .put(EnchantmentTarget.BOW, "bow")
            .put(EnchantmentTarget.WEARABLE, "wearable")
            .put(EnchantmentTarget.ALL, "vanishable") // according to VanishingCurseEnchantment
            .build();

    public static String getEnchantmentTargetName(EnchantmentTarget target) {
        return ENCHANTMENT_TARGET_NAMES.getOrDefault(target, target.name().toLowerCase(Locale.ROOT));
    }

    // Equation enchantment costs follow is a * level + b, so we can easily retrieve a and b by passing zero level
    private static JsonObject generateEnchantmentMinPowerCoefficients(Enchantment enchantment) {
        int b = enchantment.getMinimumPower(0);
        int a = enchantment.getMinimumPower(1) - b;

        JsonObject resultObject = new JsonObject();
        resultObject.addProperty("a", a);
        resultObject.addProperty("b", b);
        return resultObject;
    }

    private static JsonObject generateEnchantmentMaxPowerCoefficients(Enchantment enchantment) {
        int b = getMaximumPower(enchantment,0);
        int a = getMaximumPower(enchantment,1) - b;

        JsonObject resultObject = new JsonObject();
        resultObject.addProperty("a", a);
        resultObject.addProperty("b", b);
        return resultObject;
    }

    @Override
    public String getDataName() {
        return "enchantments";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultsArray = new JsonArray();
        Registry<Enchantment> enchantmentRegistry = Registry.ENCHANTMENT;
        enchantmentRegistry.stream()
                .forEach(enchantment -> resultsArray.add(generateEnchantment(enchantmentRegistry, enchantment)));
        return resultsArray;
    }

    public static JsonObject generateEnchantment(Registry<Enchantment> registry, Enchantment enchantment) {
        JsonObject enchantmentDesc = new JsonObject();
        Identifier registryKey = registry.getId(enchantment);

        enchantmentDesc.addProperty("id", registry.getRawId(enchantment));
        enchantmentDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());
        enchantmentDesc.addProperty("displayName", DGU.translateText(enchantment.getTranslationKey()));

        enchantmentDesc.addProperty("maxLevel", enchantment.getMaximumLevel());
        enchantmentDesc.add("minCost", generateEnchantmentMinPowerCoefficients(enchantment));
        enchantmentDesc.add("maxCost", generateEnchantmentMaxPowerCoefficients(enchantment));

        enchantmentDesc.addProperty("treasureOnly", enchantment.isTreasure());
        enchantmentDesc.addProperty("curse", enchantment.isCursed());

        List<Enchantment> incompatibleEnchantments = new ArrayList<>();
        for (Enchantment other : (Iterable<Enchantment>) registry) {
            if (enchantment.isDifferent(other) && other != enchantment) {
                incompatibleEnchantments.add(other);
            }
        }

        JsonArray excludes = new JsonArray();
        for (Enchantment excludedEnchantment : incompatibleEnchantments) {
            Identifier otherKey = registry.getId(excludedEnchantment);
            excludes.add(Objects.requireNonNull(otherKey).getPath());
        }
        enchantmentDesc.add("exclude", excludes);

        enchantmentDesc.addProperty("category", getEnchantmentTargetName(enchantment.target));
        enchantmentDesc.addProperty("weight", enchantment.getRarity().getChance());
        enchantmentDesc.addProperty("tradeable", true); // the first non-tradeable enchant came in 1.16, soul speed
        enchantmentDesc.addProperty("discoverable", true); // the first non-enchantable enchant came in 1.16, soul speed

        return enchantmentDesc;
    }

    private static int getMaximumPower(Enchantment ench, int level) {
        return ench.getMinimumPower(level) + 5;
    }
}
