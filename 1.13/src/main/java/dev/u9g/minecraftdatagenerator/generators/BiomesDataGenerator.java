package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.util.Locale;
import java.util.Objects;

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
            case "ocean" -> 112;
            case "plains" -> 9286496;
            case "desert" -> 16421912;
            case "mountains" -> 6316128;
            case "forest" -> 353825;
            case "taiga" -> 747097;
            case "swamp" -> 522674;
            case "river" -> 255;
            case "nether_wastes" -> 12532539;
            case "the_end" -> 8421631;
            case "frozen_ocean" -> 7368918;
            case "frozen_river" -> 10526975;
            case "snowy_tundra" -> 16777215;
            case "snowy_mountains" -> 10526880;
            case "mushroom_fields" -> 16711935;
            case "mushroom_field_shore" -> 10486015;
            case "beach" -> 16440917;
            case "desert_hills" -> 13786898;
            case "wooded_hills" -> 2250012;
            case "taiga_hills" -> 1456435;
            case "mountain_edge" -> 7501978;
            case "jungle" -> 5470985;
            case "jungle_hills" -> 2900485;
            case "jungle_edge" -> 6458135;
            case "deep_ocean" -> 48;
            case "stone_shore" -> 10658436;
            case "snowy_beach" -> 16445632;
            case "birch_forest" -> 3175492;
            case "birch_forest_hills" -> 2055986;
            case "dark_forest" -> 4215066;
            case "snowy_taiga" -> 3233098;
            case "snowy_taiga_hills" -> 2375478;
            case "giant_tree_taiga" -> 5858897;
            case "giant_tree_taiga_hills" -> 4542270;
            case "wooded_mountains" -> 5271632;
            case "savanna" -> 12431967;
            case "savanna_plateau" -> 10984804;
            case "badlands" -> 14238997;
            case "wooded_badlands_plateau" -> 11573093;
            case "badlands_plateau" -> 13274213;
            case "small_end_islands" -> 42;
            case "end_midlands" -> 15464630;
            case "end_highlands" -> 12828041;
            case "end_barrens" -> 9474162;
            case "warm_ocean" -> 172;
            case "lukewarm_ocean" -> 144;
            case "cold_ocean" -> 2105456;
            case "deep_warm_ocean" -> 80;
            case "deep_lukewarm_ocean" -> 64;
            case "deep_cold_ocean" -> 2105400;
            case "deep_frozen_ocean" -> 4210832;
            case "the_void" -> 0;
            case "sunflower_plains" -> 11918216;
            case "desert_lakes" -> 16759872;
            case "gravelly_mountains" -> 8947848;
            case "flower_forest" -> 2985545;
            case "taiga_mountains" -> 3378817;
            case "swamp_hills" -> 3145690;
            case "ice_spikes" -> 11853020;
            case "modified_jungle" -> 8102705;
            case "modified_jungle_edge" -> 9089855;
            case "tall_birch_forest" -> 5807212;
            case "tall_birch_hills" -> 4687706;
            case "dark_forest_hills" -> 6846786;
            case "snowy_taiga_mountains" -> 5864818;
            case "giant_spruce_taiga" -> 8490617;
            case "giant_spruce_taiga_hills" -> 7173990;
            case "modified_gravelly_mountains" -> 7903352;
            case "shattered_savanna" -> 15063687;
            case "shattered_savanna_plateau" -> 13616524;
            case "eroded_badlands" -> 16739645;
            case "modified_wooded_badlands_plateau" -> 14204813;
            case "modified_badlands_plateau" -> 15905933;
            case "bamboo_jungle" -> 7769620;
            case "bamboo_jungle_hills" -> 3884810;
            case "nether" -> 16711680;
            default -> throw new Error("Unexpected biome, with name: '" + biomeName + "'");
        };
    }

    public static JsonObject generateBiomeInfo(Registry<Biome> registry, Biome biome) {
        JsonObject biomeDesc = new JsonObject();
        Identifier registryKey = registry.getId(biome);
        String localizationKey = String.format("biome.%s.%s", Objects.requireNonNull(registryKey).getNamespace(), registryKey.getPath());

        biomeDesc.addProperty("id", registry.getRawId(biome));
        biomeDesc.addProperty("name", registryKey.getPath());

        biomeDesc.addProperty("category", biome.getCategory().name().toLowerCase(Locale.ENGLISH));
        biomeDesc.addProperty("temperature", biome.getTemperature());
        biomeDesc.addProperty("precipitation", biome.getPrecipitation().name().toLowerCase(Locale.ENGLISH));
        biomeDesc.addProperty("depth", biome.getDepth());
        biomeDesc.addProperty("dimension", guessBiomeDimensionFromCategory(biome));
        biomeDesc.addProperty("displayName", DGU.translateText(localizationKey));
        biomeDesc.addProperty("color", getBiomeColorFor(registryKey.getPath()));
        biomeDesc.addProperty("rainfall", biome.getRainfall());

        return biomeDesc;
    }

    @Override
    public String getDataName() {
        return "biomes";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray biomesArray = new JsonArray();
        Registry<Biome> biomeRegistry = Registry.BIOME;

        biomeRegistry.stream()
                .map(biome -> generateBiomeInfo(biomeRegistry, biome))
                .forEach(biomesArray::add);
        return biomesArray;
    }
}
