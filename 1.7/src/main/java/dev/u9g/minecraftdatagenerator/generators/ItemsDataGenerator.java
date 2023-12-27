package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.u9g.minecraftdatagenerator.mixin.VariantBlockItemAccessor;
import dev.u9g.minecraftdatagenerator.mixin.accessor.ItemAccessor;
import dev.u9g.minecraftdatagenerator.util.DGU;
import dev.u9g.minecraftdatagenerator.util.Registries;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.VariantBlockItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        itemDesc.addProperty("id", Registries.ITEMS.getRawId(item));

        String name = Registries.ITEMS.getId(item);
        itemDesc.addProperty("name", name == null ? ((ItemAccessor)item).translationKey() : name.replace("minecraft:", ""));

        itemDesc.addProperty("displayName", item.getDisplayName(DGU.stackFor(item)));
        itemDesc.addProperty("stackSize", item.getMaxCount());

        List<EnchantmentTarget> enchantmentTargets = getApplicableEnchantmentTargets(item);

        if (item.isDamageable()) {
            JsonArray enchantCategoriesArray = new JsonArray();
            for (EnchantmentTarget target : enchantmentTargets) {
                enchantCategoriesArray.add(new JsonPrimitive(EnchantmentsDataGenerator.getEnchantmentTargetName(target)));
            }
            if (enchantCategoriesArray.size() > 0) {
                itemDesc.add("enchantCategories", enchantCategoriesArray);
            }
        }

        if (item.isDamageable()) {
            List<Item> repairWithItems = calculateItemsToRepairWith(item);

            JsonArray fixedWithArray = new JsonArray();
            for (Item repairWithItem : repairWithItems) {
                String repairWithName = Registries.ITEMS.getId(repairWithItem);
                fixedWithArray.add(new JsonPrimitive(Objects.requireNonNull(repairWithName)));
            }
            if (fixedWithArray.size() > 0) {
                itemDesc.add("repairWith", fixedWithArray);
            }

            int maxDurability = item.getMaxDamage();
            itemDesc.addProperty("maxDurability", maxDurability);
        }

        if (item instanceof VariantBlockItem) {
            JsonArray variations = new JsonArray();
            VariantBlockItem it = (VariantBlockItem)item;
            int i = 0;
            JsonObject obj = new JsonObject();
            for (String variant : ((VariantBlockItemAccessor)it).variants()) {
                ItemStack stack = new ItemStack(item, 1, i);
                obj.add("id", new JsonPrimitive(i));
                obj.add("name", new JsonPrimitive(variant));
                obj.add("displayName", new JsonPrimitive(DGU.translateText(it.getTranslationKey(stack)+".name")));
                variations.add(obj);
                i++;
            }
            itemDesc.add("variations", variations);
        }

        return itemDesc;
    }
}
