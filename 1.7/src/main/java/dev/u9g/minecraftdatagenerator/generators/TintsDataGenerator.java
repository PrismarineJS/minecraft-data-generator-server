package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.u9g.minecraftdatagenerator.mixin.accessor.BiomeAccessor;
import dev.u9g.minecraftdatagenerator.util.Registries;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;

import java.util.*;

public class TintsDataGenerator implements IDataGenerator {
    public static BiomeTintColors generateBiomeTintColors() {
        BiomeTintColors colors = new BiomeTintColors();

        for (Biome biome : Registries.BIOMES) {
            double d = MathHelper.clamp(biome.temperature, 0.0f, 1.0f);
            double e = MathHelper.clamp(biome.downfall, 0.0f, 1.0f);

            int biomeGrassColor = GrassColors.getColor(d, e);
            int biomeFoliageColor = FoliageColors.getColor(d, e);
            int biomeWaterColor = ((BiomeAccessor) biome).waterColor();

            colors.grassColoursMap.computeIfAbsent(biomeGrassColor, k -> new ArrayList<>()).add(biome);
            colors.foliageColoursMap.computeIfAbsent(biomeFoliageColor, k -> new ArrayList<>()).add(biome);
            colors.waterColourMap.computeIfAbsent(biomeWaterColor, k -> new ArrayList<>()).add(biome);
        }
        return colors;
    }

    public static Map<Block, Integer> generateConstantTintColors() {
        Map<Block, Integer> resultColors = new LinkedHashMap<>();
        // FIXME: ?
        // resultColors.put(Blocks.BIRCH_LEAVES, FoliageColors.getBirchColor());
        // resultColors.put(Blocks.SPRUCE_LEAVES, FoliageColors.getSpruceColor());

        resultColors.put(Registries.BLOCKS.get("waterlily"), Blocks.LILY_PAD.getColor());
        // FIXME: ?
        // resultColors.put(Blocks.ATTACHED_MELON_STEM, getBlockColor(Blocks.ATTACHED_MELON_STEM));
        // resultColors.put(Blocks.ATTACHED_PUMPKIN_STEM, getBlockColor(Blocks.ATTACHED_PUMPKIN_STEM));

        //not really constant, depend on the block age, but kinda have to be handled since textures are literally white without them
        resultColors.put(Registries.BLOCKS.get("melon_stem"), Blocks.MELON_STEM.getColor());
        resultColors.put(Registries.BLOCKS.get("pumpkin_stem"), Blocks.PUMPKIN_STEM.getColor());

        return resultColors;
    }

    private static JsonObject encodeBiomeColorMap(Map<Integer, List<Biome>> colorsMap) {
        JsonArray resultColorsArray = new JsonArray();
        for (Map.Entry<Integer, List<Biome>> entry : colorsMap.entrySet()) {
            JsonObject entryObject = new JsonObject();

            JsonArray keysArray = new JsonArray();
            for (Biome biome : entry.getValue()) {
                keysArray.add(new JsonPrimitive(biome.name));
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
            keysArray.add(new JsonPrimitive(entry.getKey()));

            entryObject.add("keys", keysArray);
            entryObject.addProperty("color", entry.getValue());
            resultColorsArray.add(entryObject);
        }

        JsonObject resultObject = new JsonObject();
        resultObject.add("data", resultColorsArray);
        return resultObject;
    }

    private static JsonObject encodeBlocksColorMap(Map<Block, Integer> colorsMap) {
        JsonArray resultColorsArray = new JsonArray();
        for (Map.Entry<Block, Integer> entry : colorsMap.entrySet()) {
            JsonObject entryObject = new JsonObject();
            JsonArray keysArray = new JsonArray();
            String registryKey = Registries.BLOCKS.getId(entry.getKey());
            keysArray.add(new JsonPrimitive(Objects.requireNonNull(registryKey).replace("minecraft:", "")));

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
        BiomeTintColors biomeTintColors = generateBiomeTintColors();
        Map<Block, Integer> constantTintColors = generateConstantTintColors();

        JsonObject resultObject = new JsonObject();

        resultObject.add("grass", encodeBiomeColorMap(biomeTintColors.grassColoursMap));
        resultObject.add("foliage", encodeBiomeColorMap(biomeTintColors.foliageColoursMap));
        resultObject.add("water", encodeBiomeColorMap(biomeTintColors.waterColourMap));

        resultObject.add("constant", encodeBlocksColorMap(constantTintColors));

        return resultObject;
    }

    public static class BiomeTintColors {
        final Map<Integer, List<Biome>> grassColoursMap = new LinkedHashMap<>();
        final Map<Integer, List<Biome>> foliageColoursMap = new LinkedHashMap<>();
        final Map<Integer, List<Biome>> waterColourMap = new LinkedHashMap<>();
    }
}
