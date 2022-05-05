package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EffectsDataGenerator implements IDataGenerator {

    @Override
    public String getDataName() {
        return "effects";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultsArray = new JsonArray();
        Registry<StatusEffect> statusEffectRegistry = Registry.STATUS_EFFECT;
        statusEffectRegistry.forEach(effect -> resultsArray.add(generateEffect(statusEffectRegistry, effect)));
        return resultsArray;
    }

    public static JsonObject generateEffect(Registry<StatusEffect> registry, StatusEffect statusEffect) {
        JsonObject effectDesc = new JsonObject();
        Identifier registryKey = registry.getKey(statusEffect).orElseThrow().getValue();

        effectDesc.addProperty("id", registry.getRawId(statusEffect));
        effectDesc.addProperty("name", registryKey.getPath());
        effectDesc.addProperty("displayName", DGU.translateText(statusEffect.getTranslationKey()));

        effectDesc.addProperty("type", statusEffect.isBeneficial() ? "good" : "bad");
        return effectDesc;
    }
}
