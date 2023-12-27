package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.mixin.BiomeAccessor;
import dev.u9g.minecraftdatagenerator.util.Registries;
import net.minecraft.world.biome.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

public class BiomesDataGenerator implements IDataGenerator {

    private static String guessBiomeDimensionFromCategory(Biome biome) {
        if (biome instanceof NetherBiome) {
            return "nether";
        } else if (biome instanceof EndBiome) {
            return "end";
        }
        return "overworld";
    }

    private static int getBiomeColorFor(String biomeDisplayNamed) {
        if (biomeDisplayNamed.equals("Redwood Taiga Hills M")) {
            biomeDisplayNamed = "MegaTaigaHills";
        }
        String biomeDisplayName = StringUtils.join(biomeDisplayNamed.split(" "), "");
        if (biomeDisplayName.equals("Ocean") || biomeDisplayName.equals("Ocean")) return 112;
        else if (biomeDisplayName.equals("Plains") || biomeDisplayName.equals("Plains")) return 9286496;
        else if (biomeDisplayName.equals("Desert") || biomeDisplayName.equals("Desert")) return 16421912;
        else if (biomeDisplayName.equals("ExtremeHills") || biomeDisplayName.equals("Extreme Hills")) return 6316128;
        else if (biomeDisplayName.equals("Forest") || biomeDisplayName.equals("Forest")) return 353825;
        else if (biomeDisplayName.equals("Taiga") || biomeDisplayName.equals("Taiga")) return 747097;
        else if (biomeDisplayName.equals("Swampland") || biomeDisplayName.equals("Swampland")) return 522674;
        else if (biomeDisplayName.equals("River") || biomeDisplayName.equals("River")) return 255;
        else if (biomeDisplayName.equals("Hell") || biomeDisplayName.equals("Hell")) return 16711680;
        else if (biomeDisplayName.equals("TheEnd") || biomeDisplayName.equals("The End")) return 8421631;
        else if (biomeDisplayName.equals("FrozenOcean") || biomeDisplayName.equals("Frozen Ocean")) return 7368918;
        else if (biomeDisplayName.equals("FrozenRiver") || biomeDisplayName.equals("Frozen River")) return 10526975;
        else if (biomeDisplayName.equals("IcePlains") || biomeDisplayName.equals("Ice Plains")) return 16777215;
        else if (biomeDisplayName.equals("IceMountains") || biomeDisplayName.equals("Ice Mountains")) return 10526880;
        else if (biomeDisplayName.equals("MushroomIsland") || biomeDisplayName.equals("Mushroom Island")) return 16711935;
        else if (biomeDisplayName.equals("MushroomIslandShore") || biomeDisplayName.equals("Mushroom Island Shore")) return 10486015;
        else if (biomeDisplayName.equals("Beach") || biomeDisplayName.equals("Beach")) return 16440917;
        else if (biomeDisplayName.equals("DesertHills") || biomeDisplayName.equals("Desert Hills")) return 13786898;
        else if (biomeDisplayName.equals("ForestHills") || biomeDisplayName.equals("Forest Hills")) return 2250012;
        else if (biomeDisplayName.equals("TaigaHills") || biomeDisplayName.equals("Taiga Hills")) return 1456435;
        else if (biomeDisplayName.equals("ExtremeHillsEdge") || biomeDisplayName.equals("Extreme Hills Edge")) return 7501978;
        else if (biomeDisplayName.equals("Jungle") || biomeDisplayName.equals("Jungle")) return 5470985;
        else if (biomeDisplayName.equals("JungleHills") || biomeDisplayName.equals("Jungle Hills")) return 2900485;
        else if (biomeDisplayName.equals("JungleEdge") || biomeDisplayName.equals("Jungle Edge")) return 6458135;
        else if (biomeDisplayName.equals("DeepOcean") || biomeDisplayName.equals("Deep Ocean")) return 48;
        else if (biomeDisplayName.equals("StoneBeach") || biomeDisplayName.equals("Stone Beach")) return 10658436;
        else if (biomeDisplayName.equals("ColdBeach") || biomeDisplayName.equals("Cold Beach")) return 16445632;
        else if (biomeDisplayName.equals("BirchForest") || biomeDisplayName.equals("Birch Forest")) return 3175492;
        else if (biomeDisplayName.equals("BirchForestHills") || biomeDisplayName.equals("Birch Forest Hills")) return 2055986;
        else if (biomeDisplayName.equals("RoofedForest") || biomeDisplayName.equals("Roofed Forest")) return 4215066;
        else if (biomeDisplayName.equals("ColdTaiga") || biomeDisplayName.equals("Cold Taiga")) return 3233098;
        else if (biomeDisplayName.equals("ColdTaigaHills") || biomeDisplayName.equals("Cold Taiga Hills")) return 2375478;
        else if (biomeDisplayName.equals("MegaTaiga") || biomeDisplayName.equals("Mega Taiga")) return 5858897;
        else if (biomeDisplayName.equals("MegaTaigaHills") || biomeDisplayName.equals("Mega Taiga Hills")) return 4542270;
        else if (biomeDisplayName.equals("ExtremeHills+") || biomeDisplayName.equals("Extreme Hills+")) return 5271632;
        else if (biomeDisplayName.equals("Savanna") || biomeDisplayName.equals("Savanna")) return 12431967;
        else if (biomeDisplayName.equals("SavannaPlateau") || biomeDisplayName.equals("Savanna Plateau")) return 10984804;
        else if (biomeDisplayName.equals("Mesa") || biomeDisplayName.equals("Mesa")) return 14238997;
        else if (biomeDisplayName.equals("MesaPlateauF") || biomeDisplayName.equals("Mesa Plateau F")) return 11573093;
        else if (biomeDisplayName.equals("MesaPlateau") || biomeDisplayName.equals("Mesa Plateau")) return 13274213;
        else if (biomeDisplayName.equals("TheEnd-Floatingislands") || biomeDisplayName.equals("The End - Floating islands")) return 8421631;
        else if (biomeDisplayName.equals("TheEnd-Mediumisland") || biomeDisplayName.equals("The End - Medium island")) return 8421631;
        else if (biomeDisplayName.equals("TheEnd-Highisland") || biomeDisplayName.equals("The End - High island")) return 8421631;
        else if (biomeDisplayName.equals("TheEnd-Barrenisland") || biomeDisplayName.equals("The End - Barren island")) return 8421631;
        else if (biomeDisplayName.equals("WarmOcean") || biomeDisplayName.equals("Warm Ocean")) return 172;
        else if (biomeDisplayName.equals("LukewarmOcean") || biomeDisplayName.equals("Lukewarm Ocean")) return 144;
        else if (biomeDisplayName.equals("ColdOcean") || biomeDisplayName.equals("Cold Ocean")) return 2105456;
        else if (biomeDisplayName.equals("WarmDeepOcean") || biomeDisplayName.equals("Warm Deep Ocean")) return 80;
        else if (biomeDisplayName.equals("LukewarmDeepOcean") || biomeDisplayName.equals("Lukewarm Deep Ocean")) return 64;
        else if (biomeDisplayName.equals("ColdDeepOcean") || biomeDisplayName.equals("Cold Deep Ocean")) return 2105400;
        else if (biomeDisplayName.equals("FrozenDeepOcean") || biomeDisplayName.equals("Frozen Deep Ocean")) return 4210832;
        else if (biomeDisplayName.equals("TheVoid") || biomeDisplayName.equals("The Void")) return 0;
        else if (biomeDisplayName.equals("SunflowerPlains") || biomeDisplayName.equals("Sunflower Plains")) return 11918216;
        else if (biomeDisplayName.equals("DesertM") || biomeDisplayName.equals("Desert M")) return 16759872;
        else if (biomeDisplayName.equals("ExtremeHillsM") || biomeDisplayName.equals("Extreme Hills M")) return 8947848;
        else if (biomeDisplayName.equals("FlowerForest") || biomeDisplayName.equals("Flower Forest")) return 2985545;
        else if (biomeDisplayName.equals("TaigaM") || biomeDisplayName.equals("Taiga M")) return 3378817;
        else if (biomeDisplayName.equals("SwamplandM") || biomeDisplayName.equals("Swampland M")) return 3145690;
        else if (biomeDisplayName.equals("IcePlainsSpikes") || biomeDisplayName.equals("Ice Plains Spikes")) return 11853020;
        else if (biomeDisplayName.equals("JungleM") || biomeDisplayName.equals("Jungle M")) return 8102705;
        else if (biomeDisplayName.equals("JungleEdgeM") || biomeDisplayName.equals("Jungle Edge M")) return 9089855;
        else if (biomeDisplayName.equals("BirchForestM") || biomeDisplayName.equals("Birch Forest M")) return 5807212;
        else if (biomeDisplayName.equals("BirchForestHillsM") || biomeDisplayName.equals("Birch Forest Hills M")) return 4687706;
        else if (biomeDisplayName.equals("RoofedForestM") || biomeDisplayName.equals("Roofed Forest M")) return 6846786;
        else if (biomeDisplayName.equals("ColdTaigaM") || biomeDisplayName.equals("Cold Taiga M")) return 5864818;
        else if (biomeDisplayName.equals("MegaSpruceTaiga") || biomeDisplayName.equals("Mega Spruce Taiga")) return 8490617;
        else if (biomeDisplayName.equals("MegaSpruceTaiga(Hills)") || biomeDisplayName.equals("Mega Spruce Taiga (Hills)")) return 7173990;
        else if (biomeDisplayName.equals("ExtremeHills+M") || biomeDisplayName.equals("Extreme Hills+ M")) return 7903352;
        else if (biomeDisplayName.equals("SavannaM") || biomeDisplayName.equals("Savanna M")) return 15063687;
        else if (biomeDisplayName.equals("SavannaPlateauM") || biomeDisplayName.equals("Savanna Plateau M")) return 13616524;
        else if (biomeDisplayName.equals("Mesa(Bryce)") || biomeDisplayName.equals("Mesa (Bryce)")) return 16739645;
        else if (biomeDisplayName.equals("MesaPlateauFM") || biomeDisplayName.equals("Mesa Plateau F M")) return 14204813;
        else if (biomeDisplayName.equals("MesaPlateauM") || biomeDisplayName.equals("Mesa Plateau M")) return 15905933;
        else if (biomeDisplayName.equals("BambooJungle") || biomeDisplayName.equals("Bamboo Jungle")) return 7769620;
        else if (biomeDisplayName.equals("BambooJungleHills") || biomeDisplayName.equals("Bamboo Jungle Hills")) return 3884810;
        throw new Error("Unexpected biome, with name: '"+biomeDisplayName+"'");
    }

    public static JsonObject generateBiomeInfo(Biome biome) {
        JsonObject biomeDesc = new JsonObject();
//        Identifier registryKey = registry.getIdentifier(biome);

        biomeDesc.addProperty("id", Registries.BIOMES.getRawId(biome));
        biomeDesc.addProperty("name", String.join("_", ((BiomeAccessor)biome).name().toLowerCase(Locale.ENGLISH).split(" ")));
        biomeDesc.addProperty("category", category(biome));
        biomeDesc.addProperty("temperature", biome.temperature);
        biomeDesc.addProperty("precipitation", precipitation(biome));
        biomeDesc.addProperty("depth", biome.depth);
        biomeDesc.addProperty("dimension", guessBiomeDimensionFromCategory(biome));
        biomeDesc.addProperty("displayName", ((BiomeAccessor)biome).name());
        biomeDesc.addProperty("color", getBiomeColorFor(((BiomeAccessor)biome).name()));
        biomeDesc.addProperty("rainfall", biome.downfall);

        return biomeDesc;
    }

    @Override
    public String getDataName() {
        return "biomes";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray biomesArray = new JsonArray();

        for (Biome biome : Registries.BIOMES) {
            biomesArray.add(generateBiomeInfo(biome));
        }
        return biomesArray;
    }

    private static String category(Biome biome) {
        if (biome instanceof ForestBiome) {
            return "forest";
        } else if (biome instanceof OceanBiome) {
            return "ocean";
        } else if (biome instanceof PlainsBiome) {
            return "plains";
        } else if (biome instanceof DesertBiome) {
            return "desert";
        } else if (biome instanceof ExtremeHillsBiome) {
            return "extreme_hills";
        } else if (biome instanceof TaigaBiome) {
            return "taiga";
        } else if (biome instanceof SwampBiome) {
            return "swamp";
        } else if (biome instanceof RiverBiome) {
            return "river";
        } else if (biome instanceof NetherBiome) {
            return "nether";
        } else if (biome instanceof EndBiome) {
            return "the_end";
        } else if (biome instanceof IceBiome) {
            return "icy";
        } else if (biome instanceof MushroomBiome) {
            return "mushroom";
        } else if (biome instanceof BeachBiome) {
            return "beach";
        } else if (biome instanceof JungleBiome) {
            return "jungle";
        } else if (biome instanceof SavannaBiome) {
            return "savanna";
        } else if (biome instanceof MesaBiome) {
            return "mesa";
        } else if (biome instanceof StoneBeachBiome) {
            return "none"; // Should StoneBeachBiome be beach too? this is how it is now in mcdata
        }
        throw new Error("Unable to find biome category for " + biome.getClass().getName());
    }

    private static String precipitation(Biome biome) {
        float rainfall = biome.downfall;
        float temperature = biome.temperature;
        if (rainfall == 0) {
            return "none";
        } else if (temperature < 0.2f) {
            return "snow";
        }
        return "rain";
    }
}
