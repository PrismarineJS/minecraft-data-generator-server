package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.mixin.TheEndBiomeDataAccessor;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.fabricmc.fabric.api.biome.v1.NetherBiomes;
import net.fabricmc.fabric.api.biome.v1.TheEndBiomes;
import net.fabricmc.fabric.impl.biome.TheEndBiomeData;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class BiomesDataGenerator implements IDataGenerator {

    private static String guessBiomeDimensionFromCategory(Biome biome) {
        // FIND A WAY TO FIND WHAT CATEGORY THE BIOME IS
        var key = DynamicRegistryManager.BUILTIN.get().get(Registry.BIOME_KEY).getKey(biome).orElseThrow();
        System.out.println(DynamicRegistryManager.BUILTIN.get().get(Registry.BIOME_KEY).getKey(biome).orElseThrow());
        if (NetherBiomes.canGenerateInNether(key)) {
            return "nether";
        } else if (TheEndBiomeDataAccessor.END_BARRENS_MAP().containsKey(key) || TheEndBiomeDataAccessor.END_BIOMES_MAP().containsKey(key) || TheEndBiomeDataAccessor.END_MIDLANDS_MAP().containsKey(key)) {
            return "end";
        }
        return "overworld";
    }

    public static JsonObject generateBiomeInfo(Registry<Biome> registry, Biome biome) {
        JsonObject biomeDesc = new JsonObject();
        Identifier registryKey = registry.getKey(biome).orElseThrow().getValue();
        String localizationKey = String.format("biome.%s.%s", registryKey.getNamespace(), registryKey.getPath());

        biomeDesc.addProperty("id", registry.getRawId(biome));
        biomeDesc.addProperty("name", registryKey.getPath());

        //FIXME: this...
        biomeDesc.addProperty("category", "");
        biomeDesc.addProperty("temperature", biome.getTemperature());
        biomeDesc.addProperty("precipitation", biome.getPrecipitation().getName());
        //biomeDesc.addProperty("depth", biome.getDepth()); - Doesn't exist anymore in minecraft source
        biomeDesc.addProperty("dimension", guessBiomeDimensionFromCategory(biome));
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
