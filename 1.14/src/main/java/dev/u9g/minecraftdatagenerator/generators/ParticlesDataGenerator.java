package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Objects;

public class ParticlesDataGenerator implements IDataGenerator {

    @Override
    public String getDataName() {
        return "particles";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultsArray = new JsonArray();
        Registry<ParticleType<?>>particleTypeRegistry = Registry.PARTICLE_TYPE;
        particleTypeRegistry.forEach(particleType -> resultsArray.add(generateParticleType(particleTypeRegistry, particleType)));
        return resultsArray;
    }

    public static JsonObject generateParticleType(Registry<ParticleType<?>> registry, ParticleType<?> particleType) {
        JsonObject effectDesc = new JsonObject();
        Identifier registryKey = registry.getId(particleType);

        effectDesc.addProperty("id", registry.getRawId(particleType));
        effectDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());
        return effectDesc;
    }
}
