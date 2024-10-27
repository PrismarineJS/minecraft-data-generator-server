package dev.u9g.minecraftdatagenerator.util;

import dev.u9g.minecraftdatagenerator.mixin.accessor.BiomeAccessor;
import dev.u9g.minecraftdatagenerator.mixin.accessor.EnchantmentAccessor;
import dev.u9g.minecraftdatagenerator.mixin.accessor.EntityTypeAccessor;
import dev.u9g.minecraftdatagenerator.mixin.accessor.StatusEffectAccessor;
import dev.u9g.minecraftdatagenerator.registryview.RegistryBackedRegistryView;
import dev.u9g.minecraftdatagenerator.registryview.RegistryView;
import dev.u9g.minecraftdatagenerator.registryview.TableBackedRegistryView;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.util.Language;
import net.minecraft.world.biome.Biome;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.Map;

public class Registries {
    public static final Language LANGUAGE;
    public static final RegistryView<String, Biome> BIOMES;
    public static final RegistryView<String, Block> BLOCKS;
    public static final RegistryView<String, Item> ITEMS;
    public static final RegistryView<String, StatusEffect> STATUS_EFFECTS;
    public static final RegistryView<String, Enchantment> ENCHANTMENTS;
    public static final RegistryView<String, Class<? extends Entity>> ENTITY_TYPES;

    static {
        LANGUAGE = new Language();
        BIOMES = setupBiomeRegistry();
        BLOCKS = new RegistryBackedRegistryView<>(Block.REGISTRY);
        ITEMS = new RegistryBackedRegistryView<>(Item.REGISTRY);
        STATUS_EFFECTS = setupStatusEffectRegistry();
        ENCHANTMENTS = setupEnchantmentRegistry();
        ENTITY_TYPES = setupEntityTypesRegistry();
    }

    private static RegistryView<String, Class<? extends Entity>> setupEntityTypesRegistry() {
        TableBackedRegistryView.Builder<String, Class<? extends Entity>> registry = new TableBackedRegistryView.Builder<>();
        for (Map.Entry<Integer, Class<? extends Entity>> entry : EntityTypeAccessor.ID_CLASS_MAP().entrySet()) {
            String name = EntityTypeAccessor.CLASS_NAME_MAP().get(entry.getValue());
            if (name.equals("Mob") || name.equals("Monster")) {
                continue;
            }
            registry.add(name, entry.getKey(), entry.getValue());
        }

        return registry.build();
    }

    private static RegistryView<String, Enchantment> setupEnchantmentRegistry() {
        TableBackedRegistryView.Builder<String, Enchantment> registry = new TableBackedRegistryView.Builder<>();
        for (Enchantment enchantment : EnchantmentAccessor.ALL_ENCHANTMENTS()) {
            if (enchantment == null) continue;
            String translatedName = Registries.LANGUAGE.translate(enchantment.getTranslationKey());
            registry.add(String.join("", translatedName.toLowerCase(Locale.ENGLISH).split(" ")), enchantment.id, enchantment);
        }
        return registry.build();
    }

    private static RegistryView<String, Biome> setupBiomeRegistry() {
        TableBackedRegistryView.Builder<String, Biome> builder = new TableBackedRegistryView.Builder<>();
        for (Biome biome : BiomeAccessor.BIOMESET()) {
            builder.add(biome.name, biome.id, biome);
        }
        return builder.build();
    }

    private static RegistryView<String, StatusEffect> setupStatusEffectRegistry() {
        TableBackedRegistryView.Builder<String, StatusEffect> builder = new TableBackedRegistryView.Builder<>();
        for (StatusEffect effect : StatusEffectAccessor.STATUS_EFFECTS()) {
            if (effect == null) continue;
            String[] words = Registries.LANGUAGE.translate(effect.getTranslationKey()).split(" ");
            builder.add(StringUtils.join(words, ""), effect.id, effect);
        }
        return builder.build();
    }
}
