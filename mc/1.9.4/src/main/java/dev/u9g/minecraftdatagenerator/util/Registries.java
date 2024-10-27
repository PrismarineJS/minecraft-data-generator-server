package dev.u9g.minecraftdatagenerator.util;

import dev.u9g.minecraftdatagenerator.mixin.EntityTypeAccessor;
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
    public static final SimpleRegistry<Identifier, Biome> BIOMES = Biome.REGISTRY;
    public static final SimpleRegistry<Identifier, Block> BLOCKS = Block.REGISTRY;
    public static final SimpleRegistry<Identifier, Item> ITEMS = Item.REGISTRY;
    public static final SimpleRegistry<Identifier, StatusEffect> STATUS_EFFECTS = StatusEffect.REGISTRY;
    public static final SimpleRegistry<Identifier, Enchantment> ENCHANTMENTS = Enchantment.REGISTRY;
    public static final SimpleRegistry<Identifier, Class<? extends Entity>> ENTITY_TYPES = new SimpleRegistry<>();
    public static final Language LANGUAGE = new Language();

    static {
        for (Map.Entry<Integer, Class<? extends Entity>> entry : EntityTypeAccessor.ID_CLASS_MAP().entrySet()) {
            String name = EntityTypeAccessor.CLASS_NAME_MAP().get(entry.getValue());
            if (name.equals("Mob") || name.equals("Monster")) {
                continue;
            }
            ENTITY_TYPES.add(entry.getKey(), new Identifier(name), entry.getValue());
        }
    }
}
