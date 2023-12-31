package dev.u9g.minecraftdatagenerator.generators;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.u9g.minecraftdatagenerator.util.DGU;
import dev.u9g.minecraftdatagenerator.util.Registries;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class EnchantmentsDataGenerator implements IDataGenerator {

    private static final ImmutableMap<EnchantmentTarget, String> ENCHANTMENT_TARGET_NAMES = ImmutableMap.<EnchantmentTarget, String>builder()
            .put(EnchantmentTarget.ARMOR, "armor")
            .put(EnchantmentTarget.ARMOR_FEET, "armor_feet")
            .put(EnchantmentTarget.ARMOR_LEGS, "armor_legs")
            .put(EnchantmentTarget.ARMOR_TORSO, "armor_chest")
            .put(EnchantmentTarget.ARMOR_HEAD, "armor_head")
            .put(EnchantmentTarget.WEAPON, "weapon")
            .put(EnchantmentTarget.DIGGER, "digger")
            .put(EnchantmentTarget.FISHING_ROD, "fishing_rod")
            .put(EnchantmentTarget.BREAKABLE, "breakable")
            .put(EnchantmentTarget.BOW, "bow")
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
        int b = enchantment.getMaximumPower(0);
        int a = enchantment.getMaximumPower(1) - b;

        JsonObject resultObject = new JsonObject();
        resultObject.addProperty("a", a);
        resultObject.addProperty("b", b);
        return resultObject;
    }

    public static JsonObject generateEnchantment(Enchantment enchantment) {
        JsonObject enchantmentDesc = new JsonObject();
        String registryKey = Registries.ENCHANTMENTS.getId(enchantment);

        enchantmentDesc.addProperty("id", Registries.ENCHANTMENTS.getRawId(enchantment));
        enchantmentDesc.addProperty("name", Objects.requireNonNull(registryKey));
        enchantmentDesc.addProperty("displayName", DGU.translateText(enchantment.getTranslationKey()));

        enchantmentDesc.addProperty("maxLevel", enchantment.getMaximumLevel());
        enchantmentDesc.add("minCost", generateEnchantmentMinPowerCoefficients(enchantment));
        enchantmentDesc.add("maxCost", generateEnchantmentMaxPowerCoefficients(enchantment));

        enchantmentDesc.addProperty("treasureOnly", false); // 1.9 added treasure enchants
        enchantmentDesc.addProperty("curse", false); // 1.10 added curse enchants

        List<Enchantment> incompatibleEnchantments = new ArrayList<>();
        for (Enchantment other : Registries.ENCHANTMENTS) {
            if (!enchantment.differs(other) && !other.differs(enchantment) && other != enchantment) {
                incompatibleEnchantments.add(other);
            }
        }

        JsonArray excludes = new JsonArray();
        for (Enchantment excludedEnchantment : incompatibleEnchantments) {
            String otherKey = Registries.ENCHANTMENTS.getId(excludedEnchantment);
            excludes.add(new JsonPrimitive(Objects.requireNonNull(otherKey)));
        }
        enchantmentDesc.add("exclude", excludes);

        enchantmentDesc.addProperty("category", getEnchantmentTargetName(enchantment.target));
        enchantmentDesc.addProperty("weight", enchantment.getEnchantmentType()); // see AnvilScreenhandler L209
        enchantmentDesc.addProperty("tradeable", true); // the first non-tradeable enchant came in 1.16, soul speed
        enchantmentDesc.addProperty("discoverable", true); // the first non-enchantable enchant came in 1.16, soul speed

        return enchantmentDesc;
    }

    @Override
    public String getDataName() {
        return "enchantments";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultsArray = new JsonArray();
        for (Enchantment enchantment : Registries.ENCHANTMENTS) {
            resultsArray.add(generateEnchantment(enchantment));
        }
        return resultsArray;
    }
}
