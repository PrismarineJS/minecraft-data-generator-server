package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class BiomesDataGenerator implements IDataGenerator {
    private static String guessBiomeDimensionFromCategory(RegistryKey<Biome> biome) {
        var entry = BuiltinRegistries.BIOME.getEntry(biome).orElseThrow();
        if (entry.isIn(BiomeTags.IS_OVERWORLD)) {
            return "overworld";
        } else if (entry.isIn(BiomeTags.IS_NETHER)) {
            return "nether";
        } else if (entry.isIn(BiomeTags.IS_END)) {
            return "end";
        } else {
            throw new IllegalStateException("Biome is not in any dimension: " + biome);
        }
    }

    public static JsonObject generateBiomeInfo(Registry<Biome> registry, Biome biome) {
        JsonObject biomeDesc = new JsonObject();
        RegistryKey<Biome> registryKey = registry.getKey(biome).orElseThrow();
        Identifier identifier = registryKey.getValue();
        String localizationKey = String.format("biome.%s.%s", identifier.getNamespace(), identifier.getPath());

        biomeDesc.addProperty("id", registry.getRawId(biome));
        biomeDesc.addProperty("name", identifier.getPath());

        //FIXME: this...
        biomeDesc.addProperty("category", "");
        biomeDesc.addProperty("temperature", biome.getTemperature());
        biomeDesc.addProperty("precipitation", biome.getPrecipitation().getName());
        //biomeDesc.addProperty("depth", biome.getDepth()); - Doesn't exist anymore in minecraft source
        biomeDesc.addProperty("dimension", guessBiomeDimensionFromCategory(registryKey));
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
        Registry<Biome> biomeRegistry = DynamicRegistryManager.BUILTIN.get().get(Registry.BIOME_KEY);

        biomeRegistry.stream()
                .map(biome -> generateBiomeInfo(biomeRegistry, biome))
                .forEach(biomesArray::add);
        return biomesArray;
    }
}
