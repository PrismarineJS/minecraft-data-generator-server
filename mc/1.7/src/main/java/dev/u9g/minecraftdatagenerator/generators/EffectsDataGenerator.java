package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.mixin.accessor.StatusEffectAccessor;
import dev.u9g.minecraftdatagenerator.util.DGU;
import dev.u9g.minecraftdatagenerator.util.Registries;
import net.minecraft.entity.effect.StatusEffect;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EffectsDataGenerator implements IDataGenerator {
    public static JsonObject generateEffect(StatusEffect statusEffect) {
        JsonObject effectDesc = new JsonObject();
        @NotNull String name = Objects.requireNonNull(Registries.STATUS_EFFECTS.getId(statusEffect));

        effectDesc.addProperty("id", Registries.STATUS_EFFECTS.getRawId(statusEffect));
        effectDesc.addProperty("name", name);
        effectDesc.addProperty("displayName", DGU.translateText(statusEffect.getTranslationKey()));

        effectDesc.addProperty("type", !((StatusEffectAccessor) statusEffect).negative() ? "good" : "bad");
        return effectDesc;
    }

    @Override
    public String getDataName() {
        return "effects";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultsArray = new JsonArray();
        for (StatusEffect effect : Registries.STATUS_EFFECTS) {
            resultsArray.add(generateEffect(effect));
        }
        return resultsArray;
    }
}
