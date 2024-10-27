package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.sound.Sound;

public class SoundsDataGenerator implements IDataGenerator {
    public static JsonObject generateSound(Sound soundEvent) {
        JsonObject soundDesc = new JsonObject();

        soundDesc.addProperty("id", Sound.REGISTRY.getRawId(soundEvent));
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
        Sound.REGISTRY.forEach(sound -> resultsArray.add(generateSound(sound)));
        return resultsArray;
    }
}
