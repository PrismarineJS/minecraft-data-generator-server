package me.nelonn.accessor;

import org.joml.Vector2i;

public interface LayerDefinitionAccessor {

    default Vector2i modelExporter$getMaterial() {
        throw new UnsupportedOperationException();
    }

}
