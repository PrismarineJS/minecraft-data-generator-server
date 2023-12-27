package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.particle.ParticleType;

public class ParticlesDataGenerator implements IDataGenerator {

    @Override
    public String getDataName() {
        return "particles";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultsArray = new JsonArray();
        int i = 0;
        for (ParticleType particleType : ParticleType.values()) {
            resultsArray.add(generateParticleType(i++, particleType));
        }
        return resultsArray;
    }

    public static JsonObject generateParticleType(int id, ParticleType particleType) {
        JsonObject effectDesc = new JsonObject();

        effectDesc.addProperty("id", id);
        effectDesc.addProperty("name", particleType.getName());
        return effectDesc;
    }
}
