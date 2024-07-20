package me.nelonn.accessor;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

import java.util.Map;

public interface EntityModelSetAccessor {

    default Map<ModelLayerLocation, LayerDefinition> modelExporter$getRoots() {
        throw new UnsupportedOperationException();
    }

}
