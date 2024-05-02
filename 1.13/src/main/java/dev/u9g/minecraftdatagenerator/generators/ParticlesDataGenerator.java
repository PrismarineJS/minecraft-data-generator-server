package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Objects;

public class ParticlesDataGenerator implements IDataGenerator {
    public static JsonObject generateParticleType(ParticleType<?> particleType) {
        JsonObject effectDesc = new JsonObject();
        Identifier registryKey = Registry.PARTICLE_TYPE.getId(particleType);

        effectDesc.addProperty("id", Registry.PARTICLE_TYPE.getRawId(particleType));
        effectDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());
        return effectDesc;
    }

    @Override
    public String getDataName() {
        return "particles";
    }

    @SuppressWarnings("unchecked")
    @Override
    public JsonArray generateDataJson() {
        JsonArray resultsArray = new JsonArray();
        Registry.PARTICLE_TYPE.forEach(particleType ->
                resultsArray.add(generateParticleType((ParticleType<?>) particleType)));
        return resultsArray;
    }
}
