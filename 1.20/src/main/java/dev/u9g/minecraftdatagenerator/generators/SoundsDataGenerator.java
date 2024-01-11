package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;

public class SoundsDataGenerator implements IDataGenerator {

    public static JsonObject generateSound(Registry<SoundEvent> registry, SoundEvent soundEvent) {
        JsonObject soundDesc = new JsonObject();

        soundDesc.addProperty("id", registry.getRawId(soundEvent));
        soundDesc.addProperty("name", soundEvent.getId().getPath());

        return soundDesc;
    }

    @Override
    public String getDataName() {
        return "sounds";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultsArray = new JsonArray();
        Registry<SoundEvent> soundEventRegistry = DGU.getWorld().getRegistryManager().get(RegistryKeys.SOUND_EVENT);
        soundEventRegistry.forEach(sound -> resultsArray.add(generateSound(soundEventRegistry, sound)));
        return resultsArray;
    }
}
