package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.stream.Collectors;

public class ItemsDataGenerator implements IDataGenerator {

    private static List<Item> calculateItemsToRepairWith(Registry<Item> itemRegistry, Item sourceItem) {
        ItemStack sourceItemStack = sourceItem.getDefaultStack();
        return itemRegistry.stream()
                .filter(otherItem -> sourceItem.canRepair(sourceItemStack, otherItem.getDefaultStack()))
                .collect(Collectors.toList());
    }

    private static Set<String> getApplicableEnchantmentTargets(RegistryEntry<Item> sourceItem) {
        return DGU.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).stream()
                .map(Enchantment::getApplicableItems)
                .filter(sourceItem::isIn)
                .map(EnchantmentsDataGenerator::getEnchantmentTargetName)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public static JsonObject generateItem(Registry<Item> itemRegistry, Item item) {
        JsonObject itemDesc = new JsonObject();
        Identifier registryKey = itemRegistry.getKey(item).orElseThrow().getValue();

        itemDesc.addProperty("id", itemRegistry.getRawId(item));
        itemDesc.addProperty("name", registryKey.getPath());

        itemDesc.addProperty("displayName", DGU.translateText(item.getTranslationKey()));
        itemDesc.addProperty("stackSize", item.getMaxCount());

        JsonArray enchantCategoriesArray = new JsonArray();
        getApplicableEnchantmentTargets(itemRegistry.getEntry(item)).forEach(enchantCategoriesArray::add);
        if (!enchantCategoriesArray.isEmpty()) {
            itemDesc.add("enchantCategories", enchantCategoriesArray);
        }

        if (item.getComponents().contains(DataComponentTypes.DAMAGE)) {
            List<Item> repairWithItems = calculateItemsToRepairWith(itemRegistry, item);

            JsonArray fixedWithArray = new JsonArray();
            for (Item repairWithItem : repairWithItems) {
                Identifier repairWithName = itemRegistry.getKey(repairWithItem).orElseThrow().getValue();
                fixedWithArray.add(repairWithName.getPath());
            }
            if (!fixedWithArray.isEmpty()) {
                itemDesc.add("repairWith", fixedWithArray);
            }

            int maxDurability = Objects.requireNonNull(item.getComponents().get(DataComponentTypes.MAX_DAMAGE));
            itemDesc.addProperty("maxDurability", maxDurability);
        }
        return itemDesc;
    }

    @Override
    public String getDataName() {
        return "items";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultArray = new JsonArray();
        Registry<Item> itemRegistry = DGU.getWorld().getRegistryManager().get(RegistryKeys.ITEM);
        itemRegistry.stream().forEach(item -> resultArray.add(generateItem(itemRegistry, item)));
        return resultArray;
    }
}
