package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.Identifier;
import net.minecraft.registry.entry.RegistryEntry;


import java.util.List;
import java.util.Set;

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
        Identifier registryKey = registry.getKey(enchantment).orElseThrow().getValue();

        enchantmentDesc.addProperty("id", registry.getRawId(enchantment));
        enchantmentDesc.addProperty("name", registryKey.getPath());
        String displayName = Enchantment.getName(registry.getEntry(enchantment), 1).getString();
        displayName = displayName.replaceAll(" I$", "");
        enchantmentDesc.addProperty("displayName", displayName);

        enchantmentDesc.addProperty("maxLevel", enchantment.getMaxLevel());
        enchantmentDesc.add("minCost", generateEnchantmentMinPowerCoefficients(enchantment));
        enchantmentDesc.add("maxCost", generateEnchantmentMaxPowerCoefficients(enchantment));

        // isTreasure() removed in 1.21 :(
        // if you find a better way to get whether an enchantment is treasure that doesn't rely on a manually updated list, please update it.
        enchantmentDesc.addProperty("treasureOnly", TREASURE_ENCHANTMENT_NAMES.contains(registryKey.getPath()));

        // isCursed() removed in 1.21 :(
        // if you find a better way to get whether a enchantment is cursed that doesn't rely on a manually updated list please update it.
        enchantmentDesc.addProperty("curse", CURSE_ENCHANTMENT_NAMES.contains(registryKey.getPath()));

        List<Enchantment> incompatibleEnchantments = registry.stream()
                .filter(other -> {
                    RegistryEntry<Enchantment> enchantmentEntry = registry.getEntry(enchantment);
                    RegistryEntry<Enchantment> otherEntry = registry.getEntry(other);
                    return !Enchantment.canBeCombined(enchantmentEntry, otherEntry);
                })
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

        // isAvailableForEnchantedBookOffer() removed in 1.21 :(
        // if you find a better way to get whether an enchantment is tradeable that doesn't rely on a manually updated list, please update it.
        enchantmentDesc.addProperty("tradeable", !NON_TRADEABLE_ENCHANTMENTS.contains(registryKey.getPath()));
        
        // isAvailableForRandomSelection() removed in 1.21 :(
        // if you find a better way to get whether an enchantment is discoverable that doesn't rely on a manually updated list, please update it.
        enchantmentDesc.addProperty("discoverable", !NON_DISCOVERABLE_ENCHANTMENTS.contains(registryKey.getPath()));

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

    
    // for now need to manually add new curse enchantments
    private static final Set<String> CURSE_ENCHANTMENT_NAMES = Set.of(
        "binding_curse",
        "vanishing_curse"
    );


    // for now need to manually add new treasure enchantments(enchantments that can't be gotten from enchanting table)
    private static final Set<String> TREASURE_ENCHANTMENT_NAMES = Set.of(
        "frost_walker",
        "binding_curse",
        "vanishing_curse",
        "mending",
        "soul_speed",
        "swift_sneak",
        "wind_burst"
    );

    // for now need to manually add non-tradeable enchantments (enchants you cannot get from villagers)
    private static final Set<String> NON_TRADEABLE_ENCHANTMENTS = Set.of(
        "soul_speed",
        "swift_sneak",
        "wind_burst"
    );

    // for now need to manually add non-discoverable enchantments (enchants you cannot find in random loot chests like desert temple, jungle temple, stroghold, etc.)
    private static final Set<String> NON_DISCOVERABLE_ENCHANTMENTS = Set.of(
        "soul_speed",
        "swift_sneak",
        "wind_burst"
    );

}
