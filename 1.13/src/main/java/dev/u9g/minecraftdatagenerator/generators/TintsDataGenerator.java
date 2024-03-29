package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.clientsideannoyances.BlockColors;
import dev.u9g.minecraftdatagenerator.clientsideannoyances.FoliageColors;
import dev.u9g.minecraftdatagenerator.clientsideannoyances.GrassColors;
import dev.u9g.minecraftdatagenerator.clientsideannoyances.ServerSideRedstoneWireBlock;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.util.*;

public class TintsDataGenerator implements IDataGenerator {

    public static BiomeTintColors generateBiomeTintColors(Registry<Biome> biomeRegistry) {
        BiomeTintColors colors = new BiomeTintColors();
        for (Biome biome : (Iterable<Biome>) biomeRegistry) {
            int biomeGrassColor = GrassColors.getGrassColor(biome);
            int biomeFoliageColor = FoliageColors.getFoliageColor(biome);
            int biomeWaterColor = biome.getWaterColor();

            colors.grassColoursMap.computeIfAbsent(biomeGrassColor, k -> new ArrayList<>()).add(biome);
            colors.foliageColoursMap.computeIfAbsent(biomeFoliageColor, k -> new ArrayList<>()).add(biome);
            colors.waterColourMap.computeIfAbsent(biomeWaterColor, k -> new ArrayList<>()).add(biome);
        }
        return colors;
    }

    public static Map<Integer, Integer> generateRedstoneTintColors() {
        Map<Integer, Integer> resultColors = new HashMap<>();

        for (int redstoneLevel : RedstoneWireBlock.POWER.getValues()) {
            int color = ServerSideRedstoneWireBlock.getWireColor(redstoneLevel);
            resultColors.put(redstoneLevel, color);
        }
        return resultColors;
    }

    private static int getBlockColor(Block block, BlockColors blockColors) {
        return blockColors.method_13410(block.getDefaultState(), DGU.getWorld(), BlockPos.ORIGIN);
    }

    public static Map<Block, Integer> generateConstantTintColors() {
        Map<Block, Integer> resultColors = new HashMap<>();
        BlockColors blockColors = BlockColors.create();

        resultColors.put(Blocks.BIRCH_LEAVES, FoliageColors.getBirchColor());
        resultColors.put(Blocks.SPRUCE_LEAVES, FoliageColors.getSpruceColor());

        resultColors.put(Blocks.LILY_PAD, getBlockColor(Blocks.LILY_PAD, blockColors));
        resultColors.put(Blocks.ATTACHED_MELON_STEM, getBlockColor(Blocks.ATTACHED_MELON_STEM, blockColors));
        resultColors.put(Blocks.ATTACHED_PUMPKIN_STEM, getBlockColor(Blocks.ATTACHED_PUMPKIN_STEM, blockColors));

        //not really constant, depend on the block age, but kinda have to be handled since textures are literally white without them
        resultColors.put(Blocks.MELON_STEM, getBlockColor(Blocks.MELON_STEM, blockColors));
        resultColors.put(Blocks.PUMPKIN_STEM, getBlockColor(Blocks.PUMPKIN_STEM, blockColors));

        return resultColors;
    }

    private static JsonObject encodeBiomeColorMap(Registry<Biome> biomeRegistry, Map<Integer, List<Biome>> colorsMap) {
        JsonArray resultColorsArray = new JsonArray();
        for (Map.Entry<Integer, List<Biome>> entry : colorsMap.entrySet()) {
            JsonObject entryObject = new JsonObject();

            JsonArray keysArray = new JsonArray();
            for (Biome biome : entry.getValue()) {
                Identifier registryKey = biomeRegistry.getId(biome);
                keysArray.add(Objects.requireNonNull(registryKey).getPath());
            }

            entryObject.add("keys", keysArray);
            entryObject.addProperty("color", entry.getKey());
            resultColorsArray.add(entryObject);
        }

        JsonObject resultObject = new JsonObject();
        resultObject.add("data", resultColorsArray);
        return resultObject;
    }

    private static JsonObject encodeRedstoneColorMap(Map<Integer, Integer> colorsMap) {
        JsonArray resultColorsArray = new JsonArray();
        for (Map.Entry<Integer, Integer> entry : colorsMap.entrySet()) {
            JsonObject entryObject = new JsonObject();

            JsonArray keysArray = new JsonArray();
            keysArray.add(entry.getKey());

            entryObject.add("keys", keysArray);
            entryObject.addProperty("color", entry.getValue());
            resultColorsArray.add(entryObject);
        }

        JsonObject resultObject = new JsonObject();
        resultObject.add("data", resultColorsArray);
        return resultObject;
    }

    private static JsonObject encodeBlocksColorMap(Registry<Block> blockRegistry, Map<Block, Integer> colorsMap) {
        JsonArray resultColorsArray = new JsonArray();
        for (Map.Entry<Block, Integer> entry : colorsMap.entrySet()) {
            JsonObject entryObject = new JsonObject();

            JsonArray keysArray = new JsonArray();
            Identifier registryKey = blockRegistry.getId(entry.getKey());
            keysArray.add(Objects.requireNonNull(registryKey).getPath());

            entryObject.add("keys", keysArray);
            entryObject.addProperty("color", entry.getValue());
            resultColorsArray.add(entryObject);
        }

        JsonObject resultObject = new JsonObject();
        resultObject.add("data", resultColorsArray);
        return resultObject;
    }

    @Override
    public String getDataName() {
        return "tints";
    }

    @Override
    public JsonObject generateDataJson() {
//        DynamicRegistryManager registryManager = DynamicRegistryManager.create();
        Registry<Biome> biomeRegistry = Registry.BIOME;
        Registry<Block> blockRegistry = Registry.BLOCK;

        BiomeTintColors biomeTintColors = generateBiomeTintColors(biomeRegistry);
        Map<Integer, Integer> redstoneColors = generateRedstoneTintColors();
        Map<Block, Integer> constantTintColors = generateConstantTintColors();

        JsonObject resultObject = new JsonObject();

        resultObject.add("grass", encodeBiomeColorMap(biomeRegistry, biomeTintColors.grassColoursMap));
        resultObject.add("foliage", encodeBiomeColorMap(biomeRegistry, biomeTintColors.foliageColoursMap));
        resultObject.add("water", encodeBiomeColorMap(biomeRegistry, biomeTintColors.waterColourMap));

        resultObject.add("redstone", encodeRedstoneColorMap(redstoneColors));
        resultObject.add("constant", encodeBlocksColorMap(blockRegistry, constantTintColors));

        return resultObject;
    }

    public static class BiomeTintColors {
        final Map<Integer, List<Biome>> grassColoursMap = new HashMap<>();
        final Map<Integer, List<Biome>> foliageColoursMap = new HashMap<>();
        final Map<Integer, List<Biome>> waterColourMap = new HashMap<>();
    }
}
