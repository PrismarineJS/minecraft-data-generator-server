package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class BiomesDataGenerator implements IDataGenerator {
    private static String guessBiomeDimensionFromCategory(RegistryKey<Biome> biome) {
        var biomeRegistry = BuiltinRegistries.BIOME;
        if (biomeRegistry.getEntry(biome).orElseThrow().isIn(BiomeTags.IS_NETHER)) {
            return "nether";
        } else if (biomeRegistry.getEntry(biome).orElseThrow().isIn(BiomeTags.IS_END)) {
            return "end";
        } else {
            return "overworld";
        }
    }

    private static String guessCategoryBasedOnName(String name, String dimension) {
        if (dimension.equals("nether")) {
            return "nether";
        } else if (dimension.equals("end")) {
            return "the_end";
        }

        if (name.contains("end")) {
            System.out.println();
        }

        if (name.contains("hills")) {
            return "extreme_hills";
        } else if (name.contains("ocean")) {
            return "ocean";
        } else if (name.contains("plains")) {
            return "plains";
        } else if (name.contains("ice") || name.contains("frozen")) {
            return "ice";
        } else if (name.contains("jungle")) {
            return "jungle";
        } else if (name.contains("desert")) {
            return "desert";
        } else if (name.contains("forest") || name.contains("grove")) {
            return "forest";
        } else if (name.contains("taiga")) {
            return "taiga";
        } else if (name.contains("swamp")) {
            return "swamp";
        } else if (name.contains("river")) {
            return "river";
        } else if (name.equals("the_end")) {
            return "the_end";
        } else if (name.contains("mushroom")) {
            return "mushroom";
        } else if (name.contains("beach") || name.equals("stony_shore")) {
            return "beach";
        } else if (name.contains("savanna")) {
            return "savanna";
        } else if (name.contains("badlands")) {
            return "mesa";
        } else if (name.contains("peaks") || name.equals("snowy_slopes") || name.equals("meadow")) {
            return "mountain";
        } else if (name.equals("the_void")) {
            return "none";
        } else if (name.contains("cave") || name.equals("deep_dark")) {
            return "underground";
        } else {
            System.out.println("Unable to find biome category for biome with name: '" + name + "'");
            return "none";
        }
    }

    public static JsonObject generateBiomeInfo(Biome biome) {
        JsonObject biomeDesc = new JsonObject();
        RegistryKey<Biome> registryKey = BuiltinRegistries.BIOME.getKey(biome).orElseThrow();
        Identifier identifier = registryKey.getValue();
        String localizationKey = String.format("biome.%s.%s", identifier.getNamespace(), identifier.getPath());
        String name = identifier.getPath();
        biomeDesc.addProperty("id", BuiltinRegistries.BIOME.getRawId(biome));
        biomeDesc.addProperty("name", name);
        String dimension = guessBiomeDimensionFromCategory(registryKey);
        biomeDesc.addProperty("category", guessCategoryBasedOnName(name, dimension));
        biomeDesc.addProperty("temperature", biome.getTemperature());
        biomeDesc.addProperty("precipitation", biome.getPrecipitation().getName());
        //biomeDesc.addProperty("depth", biome.getDepth()); - Doesn't exist anymore in minecraft source
        biomeDesc.addProperty("dimension", dimension);
        biomeDesc.addProperty("displayName", DGU.translateText(localizationKey));
        biomeDesc.addProperty("color", biome.getSkyColor());
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
        BuiltinRegistries.BIOME.stream()
                .map(BiomesDataGenerator::generateBiomeInfo)
                .forEach(biomesArray::add);
        return biomesArray;
    }
}
