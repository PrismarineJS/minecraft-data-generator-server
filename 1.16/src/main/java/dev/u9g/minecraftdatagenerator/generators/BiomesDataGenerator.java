package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.util.Identifier;
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
        if (biomeName.equals("ocean")) return 112;
        else if (biomeName.equals("plains")) return 9286496;
        else if (biomeName.equals("desert")) return 16421912;
        else if (biomeName.equals("mountains")) return 6316128;
        else if (biomeName.equals("forest")) return 353825;
        else if (biomeName.equals("taiga")) return 747097;
        else if (biomeName.equals("swamp")) return 522674;
        else if (biomeName.equals("river")) return 255;
        else if (biomeName.equals("nether_wastes")) return 12532539;
        else if (biomeName.equals("the_end")) return 8421631;
        else if (biomeName.equals("frozen_ocean")) return 7368918;
        else if (biomeName.equals("frozen_river")) return 10526975;
        else if (biomeName.equals("snowy_tundra")) return 16777215;
        else if (biomeName.equals("snowy_mountains")) return 10526880;
        else if (biomeName.equals("mushroom_fields")) return 16711935;
        else if (biomeName.equals("mushroom_field_shore")) return 10486015;
        else if (biomeName.equals("beach")) return 16440917;
        else if (biomeName.equals("desert_hills")) return 13786898;
        else if (biomeName.equals("wooded_hills")) return 2250012;
        else if (biomeName.equals("taiga_hills")) return 1456435;
        else if (biomeName.equals("mountain_edge")) return 7501978;
        else if (biomeName.equals("jungle")) return 5470985;
        else if (biomeName.equals("jungle_hills")) return 2900485;
        else if (biomeName.equals("jungle_edge")) return 6458135;
        else if (biomeName.equals("deep_ocean")) return 48;
        else if (biomeName.equals("stone_shore")) return 10658436;
        else if (biomeName.equals("snowy_beach")) return 16445632;
        else if (biomeName.equals("birch_forest")) return 3175492;
        else if (biomeName.equals("birch_forest_hills")) return 2055986;
        else if (biomeName.equals("dark_forest")) return 4215066;
        else if (biomeName.equals("snowy_taiga")) return 3233098;
        else if (biomeName.equals("snowy_taiga_hills")) return 2375478;
        else if (biomeName.equals("giant_tree_taiga")) return 5858897;
        else if (biomeName.equals("giant_tree_taiga_hills")) return 4542270;
        else if (biomeName.equals("wooded_mountains")) return 5271632;
        else if (biomeName.equals("savanna")) return 12431967;
        else if (biomeName.equals("savanna_plateau")) return 10984804;
        else if (biomeName.equals("badlands")) return 14238997;
        else if (biomeName.equals("wooded_badlands_plateau")) return 11573093;
        else if (biomeName.equals("badlands_plateau")) return 13274213;
        else if (biomeName.equals("small_end_islands")) return 42;
        else if (biomeName.equals("end_midlands")) return 15464630;
        else if (biomeName.equals("end_highlands")) return 12828041;
        else if (biomeName.equals("end_barrens")) return 9474162;
        else if (biomeName.equals("warm_ocean")) return 172;
        else if (biomeName.equals("lukewarm_ocean")) return 144;
        else if (biomeName.equals("cold_ocean")) return 2105456;
        else if (biomeName.equals("deep_warm_ocean")) return 80;
        else if (biomeName.equals("deep_lukewarm_ocean")) return 64;
        else if (biomeName.equals("deep_cold_ocean")) return 2105400;
        else if (biomeName.equals("deep_frozen_ocean")) return 4210832;
        else if (biomeName.equals("the_void")) return 0;
        else if (biomeName.equals("sunflower_plains")) return 11918216;
        else if (biomeName.equals("desert_lakes")) return 16759872;
        else if (biomeName.equals("gravelly_mountains")) return 8947848;
        else if (biomeName.equals("flower_forest")) return 2985545;
        else if (biomeName.equals("taiga_mountains")) return 3378817;
        else if (biomeName.equals("swamp_hills")) return 3145690;
        else if (biomeName.equals("ice_spikes")) return 11853020;
        else if (biomeName.equals("modified_jungle")) return 8102705;
        else if (biomeName.equals("modified_jungle_edge")) return 9089855;
        else if (biomeName.equals("tall_birch_forest")) return 5807212;
        else if (biomeName.equals("tall_birch_hills")) return 4687706;
        else if (biomeName.equals("dark_forest_hills")) return 6846786;
        else if (biomeName.equals("snowy_taiga_mountains")) return 5864818;
        else if (biomeName.equals("giant_spruce_taiga")) return 8490617;
        else if (biomeName.equals("giant_spruce_taiga_hills")) return 7173990;
        else if (biomeName.equals("modified_gravelly_mountains")) return 7903352;
        else if (biomeName.equals("shattered_savanna")) return 15063687;
        else if (biomeName.equals("shattered_savanna_plateau")) return 13616524;
        else if (biomeName.equals("eroded_badlands")) return 16739645;
        else if (biomeName.equals("modified_wooded_badlands_plateau")) return 14204813;
        else if (biomeName.equals("modified_badlands_plateau")) return 15905933;
        else if (biomeName.equals("bamboo_jungle")) return 7769620;
        else if (biomeName.equals("bamboo_jungle_hills")) return 3884810;
        else if (biomeName.equals("soul_sand_valley")) return 6174768;
        else if (biomeName.equals("crimson_forest")) return 14485512;
        else if (biomeName.equals("warped_forest")) return 4821115;
        else if (biomeName.equals("basalt_deltas")) return 4208182;
        else throw new Error("Unexpected biome, with name: '"+biomeName+"'");
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
