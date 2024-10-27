package dev.u9g.minecraftdatagenerator.util;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.Biome;

public class Registries {
    public static final SimpleRegistry<Identifier, Biome> BIOMES = Biome.REGISTRY;
    public static final SimpleRegistry<Identifier, Block> BLOCKS = Block.REGISTRY;
    public static final SimpleRegistry<Identifier, Item> ITEMS = Item.REGISTRY;
    public static final SimpleRegistry<Identifier, StatusEffect> STATUS_EFFECTS = StatusEffect.REGISTRY;
    public static final SimpleRegistry<Identifier, Enchantment> ENCHANTMENTS = Enchantment.REGISTRY;
    public static final SimpleRegistry<Identifier, Class<? extends Entity>> ENTITY_TYPES = EntityType.REGISTRY;
    public static final Language LANGUAGE = new Language();
}
