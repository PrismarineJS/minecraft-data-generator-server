package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.u9g.minecraftdatagenerator.util.DGU;
import dev.u9g.minecraftdatagenerator.util.Registries;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItemsDataGenerator implements IDataGenerator {

    private static List<Item> calculateItemsToRepairWith(Item sourceItem) {
        List<Item> items = new ArrayList<>();
        for (Item otherItem : Registries.ITEMS) {
            if (sourceItem.canRepair(DGU.stackFor(sourceItem), DGU.stackFor(otherItem))) {
                items.add(otherItem);
            }
        }
        return items;
    }

    private static List<EnchantmentTarget> getApplicableEnchantmentTargets(Item sourceItem) {
        List<EnchantmentTarget> targets = new ArrayList<>();
        for (EnchantmentTarget target : EnchantmentTarget.values()) {
            if (!target.isCompatible(sourceItem)) continue;
            targets.add(target);
        }
        return targets;
    }

    @Override
    public String getDataName() {
        return "items";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultArray = new JsonArray();
        for (Item item : Registries.ITEMS) {
            resultArray.add(generateItem(item));
        }
        return resultArray;
    }

    public static JsonObject generateItem(Item item) {
        JsonObject itemDesc = new JsonObject();
        Identifier registryKey = Registries.ITEMS.getIdentifier(item);

        itemDesc.addProperty("id", Registries.ITEMS.getRawId(item));
        itemDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());

        itemDesc.addProperty("displayName", DGU.translateText(item.getTranslationKey()));
        itemDesc.addProperty("stackSize", item.getMaxCount());

        List<EnchantmentTarget> enchantmentTargets = getApplicableEnchantmentTargets(item);

        JsonArray enchantCategoriesArray = new JsonArray();
        for (EnchantmentTarget target : enchantmentTargets) {
            enchantCategoriesArray.add(new JsonPrimitive(EnchantmentsDataGenerator.getEnchantmentTargetName(target)));
        }
        if (enchantCategoriesArray.size() > 0) {
            itemDesc.add("enchantCategories", enchantCategoriesArray);
        }

        if (item.isDamageable()) {
            List<Item> repairWithItems = calculateItemsToRepairWith(item);

            JsonArray fixedWithArray = new JsonArray();
            for (Item repairWithItem : repairWithItems) {
                Identifier repairWithName = Registries.ITEMS.getIdentifier(repairWithItem);
                fixedWithArray.add(new JsonPrimitive(Objects.requireNonNull(repairWithName).getPath()));
            }
            if (fixedWithArray.size() > 0) {
                itemDesc.add("repairWith", fixedWithArray);
            }

            int maxDurability = item.getMaxDamage();
            itemDesc.addProperty("maxDurability", maxDurability);
        }
        return itemDesc;
    }
}
