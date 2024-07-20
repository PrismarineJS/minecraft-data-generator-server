package me.nelonn.mixin.client;

import me.nelonn.JsonModelExporter;
import me.nelonn.accessor.EntityModelSetAccessor;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(EntityModelSet.class)
public abstract class EntityModelSetMixin implements EntityModelSetAccessor {

    @Shadow private Map<ModelLayerLocation, LayerDefinition> roots;

    @Override
    public Map<ModelLayerLocation, LayerDefinition> modelExporter$getRoots() {
        return roots;
    }

    @Inject(method = "onResourceManagerReload", at = @At("TAIL"))
    private void modelexporter_onReload(ResourceManager resourceManager, CallbackInfo ci) {
        JsonModelExporter.export((EntityModelSet) (Object) this);
    }

}
