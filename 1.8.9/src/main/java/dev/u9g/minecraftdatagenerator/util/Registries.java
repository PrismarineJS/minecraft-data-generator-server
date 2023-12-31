package dev.u9g.minecraftdatagenerator.util;

import dev.u9g.minecraftdatagenerator.mixin.BiomeAccessor;
import dev.u9g.minecraftdatagenerator.mixin.EnchantmentAccessor;
import dev.u9g.minecraftdatagenerator.mixin.EntityTypeAccessor;
import dev.u9g.minecraftdatagenerator.mixin.StatusEffectAccessor;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.Biome;

import java.util.Map;

public class Registries {
    public static final SimpleRegistry<String, Biome> BIOMES = setupBiomeRegistry();
    public static final SimpleRegistry<Identifier, Block> BLOCKS = Block.REGISTRY;
    public static final SimpleRegistry<Identifier, Item> ITEMS = Item.REGISTRY;
    public static final SimpleRegistry<Identifier, StatusEffect> STATUS_EFFECTS = setupStatusEffectRegistry();
    public static final SimpleRegistry<Identifier, Enchantment> ENCHANTMENTS = setupEnchantmentRegistry();
    public static final SimpleRegistry<Identifier, Class<? extends Entity>> ENTITY_TYPES = setupEntityTypesRegistry();
    public static final Language LANGUAGE = new Language();

    private static SimpleRegistry<Identifier, Class<? extends Entity>> setupEntityTypesRegistry() {
        SimpleRegistry<Identifier, Class<? extends Entity>> registry = new SimpleRegistry<>();
        for (Map.Entry<Integer, Class<? extends Entity>> entry : EntityTypeAccessor.ID_CLASS_MAP().entrySet()) {
            String name = EntityTypeAccessor.CLASS_NAME_MAP().get(entry.getValue());
            if (name.equals("Mob") || name.equals("Monster")) {
                continue;
            }
            registry.add(entry.getKey(), new Identifier(name), entry.getValue());
        }

        return registry;
    }

    private static SimpleRegistry<Identifier, Enchantment> setupEnchantmentRegistry() {
        SimpleRegistry<Identifier, Enchantment> registry = new SimpleRegistry<>();
        for (Map.Entry<Identifier, Enchantment> entry : EnchantmentAccessor.ENCHANTMENT_MAP().entrySet()) {
            registry.add(entry.getValue().id, entry.getKey(), entry.getValue());
        }
        return registry;
    }

    private static SimpleRegistry<String, Biome> setupBiomeRegistry() {
        SimpleRegistry<String, Biome> registry = new SimpleRegistry<>();
        for (Biome biome : BiomeAccessor.BIOMESET()) {
            registry.add(biome.id, biome.name, biome);
        }
        return registry;
    }

    private static SimpleRegistry<Identifier, StatusEffect> setupStatusEffectRegistry() {
        SimpleRegistry<Identifier, StatusEffect> registry = new SimpleRegistry<>();
        for (Map.Entry<Identifier, StatusEffect> entry : StatusEffectAccessor.STATUS_EFFECTS_BY_ID().entrySet()) {
            registry.add(entry.getValue().id, entry.getKey(), entry.getValue());
        }
        return registry;
    }
}
