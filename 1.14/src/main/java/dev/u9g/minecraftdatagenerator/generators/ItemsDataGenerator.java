package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItemsDataGenerator implements IDataGenerator {

    private static List<Item> calculateItemsToRepairWith(Registry<Item> itemRegistry, Item sourceItem) {
        ItemStack sourceItemStack = DGU.stackFor(sourceItem);
        return itemRegistry.stream()
                .filter(otherItem -> sourceItem.canRepair(sourceItemStack, DGU.stackFor(otherItem)))
                .collect(Collectors.toList());
    }

    private static List<EnchantmentTarget> getApplicableEnchantmentTargets(Item sourceItem) {
        return Arrays.stream(EnchantmentTarget.values())
                .filter(target -> target.isAcceptableItem(sourceItem))
                .collect(Collectors.toList());
    }

    @Override
    public String getDataName() {
        return "items";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultArray = new JsonArray();
        Registry<Item> itemRegistry = Registry.ITEM;
        itemRegistry.stream().forEach(item -> resultArray.add(generateItem(itemRegistry, item)));
        return resultArray;
    }

    public static JsonObject generateItem(Registry<Item> itemRegistry, Item item) {
        JsonObject itemDesc = new JsonObject();
        Identifier registryKey = itemRegistry.getId(item);

        itemDesc.addProperty("id", itemRegistry.getRawId(item));
        itemDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());

        itemDesc.addProperty("displayName", DGU.translateText(item.getTranslationKey()));
        itemDesc.addProperty("stackSize", item.getMaxAmount());

        List<EnchantmentTarget> enchantmentTargets = getApplicableEnchantmentTargets(item);

        JsonArray enchantCategoriesArray = new JsonArray();
        for (EnchantmentTarget target : enchantmentTargets) {
            enchantCategoriesArray.add(EnchantmentsDataGenerator.getEnchantmentTargetName(target));
        }
        if (enchantCategoriesArray.size() > 0) {
            itemDesc.add("enchantCategories", enchantCategoriesArray);
        }

        if (item.canDamage()) {
            List<Item> repairWithItems = calculateItemsToRepairWith(itemRegistry, item);

            JsonArray fixedWithArray = new JsonArray();
            for (Item repairWithItem : repairWithItems) {
                Identifier repairWithName = itemRegistry.getId(repairWithItem);
                fixedWithArray.add(Objects.requireNonNull(repairWithName).getPath());
            }
            if (fixedWithArray.size() > 0) {
                itemDesc.add("repairWith", fixedWithArray);
            }

            int maxDurability = item.getMaxAmount();
            itemDesc.addProperty("maxDurability", maxDurability);
        }
        return itemDesc;
    }
}
