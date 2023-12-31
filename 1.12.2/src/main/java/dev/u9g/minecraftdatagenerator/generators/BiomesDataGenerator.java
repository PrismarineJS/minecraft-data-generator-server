package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.mixin.BiomeAccessor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;
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
        return switch (biomeDisplayName) {
            case "Ocean", "Ocean" -> 112;
            case "Plains", "Plains" -> 9286496;
            case "Desert", "Desert" -> 16421912;
            case "ExtremeHills", "Extreme Hills" -> 6316128;
            case "Forest", "Forest" -> 353825;
            case "Taiga", "Taiga" -> 747097;
            case "Swampland", "Swampland" -> 522674;
            case "River", "River" -> 255;
            case "Hell", "Hell" -> 16711680;
            case "TheEnd", "The End" -> 8421631;
            case "FrozenOcean", "Frozen Ocean" -> 7368918;
            case "FrozenRiver", "Frozen River" -> 10526975;
            case "IcePlains", "Ice Plains" -> 16777215;
            case "IceMountains", "Ice Mountains" -> 10526880;
            case "MushroomIsland", "Mushroom Island" -> 16711935;
            case "MushroomIslandShore", "Mushroom Island Shore" -> 10486015;
            case "Beach", "Beach" -> 16440917;
            case "DesertHills", "Desert Hills" -> 13786898;
            case "ForestHills", "Forest Hills" -> 2250012;
            case "TaigaHills", "Taiga Hills" -> 1456435;
            case "ExtremeHillsEdge", "Extreme Hills Edge" -> 7501978;
            case "Jungle", "Jungle" -> 5470985;
            case "JungleHills", "Jungle Hills" -> 2900485;
            case "JungleEdge", "Jungle Edge" -> 6458135;
            case "DeepOcean", "Deep Ocean" -> 48;
            case "StoneBeach", "Stone Beach" -> 10658436;
            case "ColdBeach", "Cold Beach" -> 16445632;
            case "BirchForest", "Birch Forest" -> 3175492;
            case "BirchForestHills", "Birch Forest Hills" -> 2055986;
            case "RoofedForest", "Roofed Forest" -> 4215066;
            case "ColdTaiga", "Cold Taiga" -> 3233098;
            case "ColdTaigaHills", "Cold Taiga Hills" -> 2375478;
            case "MegaTaiga", "Mega Taiga" -> 5858897;
            case "MegaTaigaHills", "Mega Taiga Hills" -> 4542270;
            case "ExtremeHills+", "Extreme Hills+" -> 5271632;
            case "Savanna", "Savanna" -> 12431967;
            case "SavannaPlateau", "Savanna Plateau" -> 10984804;
            case "Mesa", "Mesa" -> 14238997;
            case "MesaPlateauF", "Mesa Plateau F" -> 11573093;
            case "MesaPlateau", "Mesa Plateau" -> 13274213;
            case "TheEnd-Floatingislands", "The End - Floating islands" -> 8421631;
            case "TheEnd-Mediumisland", "The End - Medium island" -> 8421631;
            case "TheEnd-Highisland", "The End - High island" -> 8421631;
            case "TheEnd-Barrenisland", "The End - Barren island" -> 8421631;
            case "WarmOcean", "Warm Ocean" -> 172;
            case "LukewarmOcean", "Lukewarm Ocean" -> 144;
            case "ColdOcean", "Cold Ocean" -> 2105456;
            case "WarmDeepOcean", "Warm Deep Ocean" -> 80;
            case "LukewarmDeepOcean", "Lukewarm Deep Ocean" -> 64;
            case "ColdDeepOcean", "Cold Deep Ocean" -> 2105400;
            case "FrozenDeepOcean", "Frozen Deep Ocean" -> 4210832;
            case "TheVoid", "The Void" -> 0;
            case "SunflowerPlains", "Sunflower Plains" -> 11918216;
            case "DesertM", "Desert M" -> 16759872;
            case "ExtremeHillsM", "Extreme Hills M" -> 8947848;
            case "FlowerForest", "Flower Forest" -> 2985545;
            case "TaigaM", "Taiga M" -> 3378817;
            case "SwamplandM", "Swampland M" -> 3145690;
            case "IcePlainsSpikes", "Ice Plains Spikes" -> 11853020;
            case "JungleM", "Jungle M" -> 8102705;
            case "JungleEdgeM", "Jungle Edge M" -> 9089855;
            case "BirchForestM", "Birch Forest M" -> 5807212;
            case "BirchForestHillsM", "Birch Forest Hills M" -> 4687706;
            case "RoofedForestM", "Roofed Forest M" -> 6846786;
            case "ColdTaigaM", "Cold Taiga M" -> 5864818;
            case "MegaSpruceTaiga", "Mega Spruce Taiga" -> 8490617;
            case "MegaSpruceTaiga(Hills)", "Mega Spruce Taiga (Hills)" -> 7173990;
            case "ExtremeHills+M", "Extreme Hills+ M" -> 7903352;
            case "SavannaM", "Savanna M" -> 15063687;
            case "SavannaPlateauM", "Savanna Plateau M" -> 13616524;
            case "Mesa(Bryce)", "Mesa (Bryce)" -> 16739645;
            case "MesaPlateauFM", "Mesa Plateau F M" -> 14204813;
            case "MesaPlateauM", "Mesa Plateau M" -> 15905933;
            case "BambooJungle", "Bamboo Jungle" -> 7769620;
            case "BambooJungleHills", "Bamboo Jungle Hills" -> 3884810;
            default -> throw new Error("Unexpected biome, with name: '" + biomeDisplayName + "'");
        };
    }

    public static JsonObject generateBiomeInfo(SimpleRegistry<Identifier, Biome> registry, Biome biome) {
        JsonObject biomeDesc = new JsonObject();
        Identifier registryKey = registry.getIdentifier(biome);

        String name = String.join("_", ((BiomeAccessor) biome).name().toLowerCase(Locale.ENGLISH).split(" "));
        String displayName = ((BiomeAccessor) biome).name();
        biomeDesc.addProperty("id", registry.getRawId(biome));
        biomeDesc.addProperty("name", name);
        biomeDesc.addProperty("category", category(biome));
        biomeDesc.addProperty("temperature", biome.getTemperature());
        biomeDesc.addProperty("precipitation", precipitation(biome));
        biomeDesc.addProperty("depth", biome.getDepth());
        biomeDesc.addProperty("dimension", guessBiomeDimensionFromCategory(biome));
        biomeDesc.addProperty("displayName", displayName);
        biomeDesc.addProperty("color", getBiomeColorFor(displayName));
        biomeDesc.addProperty("rainfall", biome.getRainfall());

        return biomeDesc;
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
        } else if (biome instanceof StoneBeachBiome || biome instanceof VoidBiome) {
            return "none"; // Should StoneBeachBiome be beach too? this is how it is now in mcdata
        }
        throw new Error("Unable to find biome category for " + biome.getClass().getName());
    }

    private static String precipitation(Biome biome) {
        float rainfall = biome.getRainfall();
        float temperature = biome.getTemperature();
        if (rainfall == 0) {
            return "none";
        } else if (temperature < 0.2f) {
            return "snow";
        }
        return "rain";
    }

    @Override
    public String getDataName() {
        return "biomes";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray biomesArray = new JsonArray();
        SimpleRegistry<Identifier, Biome> biomeRegistry = Biome.REGISTRY;

        for (Biome biome : biomeRegistry) {
            biomesArray.add(generateBiomeInfo(biomeRegistry, biome));
        }
        return biomesArray;
    }
}
