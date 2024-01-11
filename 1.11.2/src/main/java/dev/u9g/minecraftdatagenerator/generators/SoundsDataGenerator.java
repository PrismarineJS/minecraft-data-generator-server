package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.sound.SoundEvent;


public class SoundsDataGenerator implements IDataGenerator {

    public static JsonObject generateSound(SoundEvent soundEvent) {
        JsonObject soundDesc = new JsonObject();

        soundDesc.addProperty("id", SoundEvent.REGISTRY.getRawId(soundEvent));
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
        SoundEvent.REGISTRY.forEach(sound -> resultsArray.add(generateSound(sound)));
        return resultsArray;
    }
}
