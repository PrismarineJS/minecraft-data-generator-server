package me.nelonn.mixin.client;

import me.nelonn.accessor.LayerDefinitionAccessor;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MaterialDefinition;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LayerDefinition.class)
public abstract class LayerDefinitionMixin implements LayerDefinitionAccessor {

    @Shadow @Final private MaterialDefinition material;

    @Override
    public Vector2i modelExporter$getMaterial() {
        return new Vector2i(material.xTexSize, material.yTexSize);
    }

}
