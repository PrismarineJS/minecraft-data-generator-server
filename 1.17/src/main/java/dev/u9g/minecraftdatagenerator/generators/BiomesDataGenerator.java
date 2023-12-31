package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class BiomesDataGenerator implements IDataGenerator {

    private static String guessBiomeDimensionFromCategory(Biome biome) {
        return switch (biome.getCategory()) {
            case NETHER -> "nether";
            case THEEND -> "end";
            default -> "overworld";
        };
    }

    private static int getBiomeColorFor(String biomeName) {
        return switch (biomeName) {
            case "the_void" -> 0;
            case "plains" -> 9286496;
            case "sunflower_plains" -> 11918216;
            case "snowy_plains" -> 16777215;
            case "ice_spikes" -> 11853020;
            case "desert" -> 16421912;
            case "swamp" -> 522674;
            case "forest" -> 353825;
            case "flower_forest" -> 2985545;
            case "birch_forest" -> 3175492;
            case "dark_forest" -> 4215066;
            case "old_growth_birch_forest" -> 5807212;
            case "old_growth_pine_taiga" -> 5858897;
            case "old_growth_spruce_taiga" -> 8490617;
            case "taiga" -> 747097;
            case "snowy_taiga" -> 3233098;
            case "savanna" -> 12431967;
            case "savanna_plateau" -> 10984804;
            case "windswept_hills" -> 6316128;
            case "windswept_gravelly_hills" -> 8947848;
            case "windswept_forest" -> 2250012;
            case "windswept_savanna" -> 15063687;
            case "jungle" -> 5470985;
            case "sparse_jungle" -> 6458135;
            case "bamboo_jungle" -> 7769620;
            case "badlands" -> 14238997;
            case "eroded_badlands" -> 16739645;
            case "wooded_badlands" -> 11573093;
            case "meadow" -> 9217136;
            case "grove" -> 14675173;
            case "snowy_slopes" -> 14348785;
            case "frozen_peaks" -> 15399931;
            case "jagged_peaks" -> 14937325;
            case "stony_peaks" -> 13750737;
            case "river" -> 255;
            case "frozen_river" -> 10526975;
            case "beach" -> 16440917;
            case "snowy_beach" -> 16445632;
            case "stony_shore" -> 10658436;
            case "warm_ocean" -> 172;
            case "lukewarm_ocean" -> 144;
            case "deep_lukewarm_ocean" -> 64;
            case "ocean" -> 112;
            case "deep_ocean" -> 48;
            case "cold_ocean" -> 2105456;
            case "deep_cold_ocean" -> 2105400;
            case "frozen_ocean" -> 7368918;
            case "deep_frozen_ocean" -> 4210832;
            case "mushroom_fields" -> 16711935;
            case "dripstone_caves" -> 12690831;
            case "lush_caves" -> 14652980;
            case "nether_wastes" -> 12532539;
            case "warped_forest" -> 4821115;
            case "crimson_forest" -> 14485512;
            case "soul_sand_valley" -> 6174768;
            case "basalt_deltas" -> 4208182;
            case "the_end" -> 8421631;
            case "end_highlands" -> 12828041;
            case "end_midlands" -> 15464630;
            case "small_end_islands" -> 42;
            case "end_barrens" -> 9474162;
            default -> throw new Error("Unexpected biome, with name: '" + biomeName + "'");
        };
    }

    public static JsonObject generateBiomeInfo(Registry<Biome> registry, Biome biome) {
        JsonObject biomeDesc = new JsonObject();
        Identifier registryKey = registry.getKey(biome).orElseThrow().getValue();
        String localizationKey = String.format("biome.%s.%s", registryKey.getNamespace(), registryKey.getPath());

        biomeDesc.addProperty("id", registry.getRawId(biome));
        biomeDesc.addProperty("name", registryKey.getPath());

        biomeDesc.addProperty("category", biome.getCategory().getName());
        biomeDesc.addProperty("temperature", biome.getTemperature());
        biomeDesc.addProperty("precipitation", biome.getPrecipitation().getName());
        biomeDesc.addProperty("depth", biome.getDepth());
        biomeDesc.addProperty("dimension", guessBiomeDimensionFromCategory(biome));
        biomeDesc.addProperty("displayName", DGU.translateText(localizationKey));
        biomeDesc.addProperty("color", getBiomeColorFor(registryKey.getPath()));
        biomeDesc.addProperty("rainfall", biome.getDownfall());

        return biomeDesc;
    }

    @Override
    public String getDataName() {
        return "biomes";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray biomesArray = new JsonArray();
        DynamicRegistryManager registryManager = DynamicRegistryManager.create();
        Registry<Biome> biomeRegistry = registryManager.get(Registry.BIOME_KEY);

        biomeRegistry.stream()
                .map(biome -> generateBiomeInfo(biomeRegistry, biome))
                .forEach(biomesArray::add);
        return biomesArray;
    }
}
