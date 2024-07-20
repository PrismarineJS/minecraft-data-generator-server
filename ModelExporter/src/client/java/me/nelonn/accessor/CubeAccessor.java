package me.nelonn.accessor;

import org.joml.Vector3f;
import org.joml.Vector4f;

public interface CubeAccessor {

    default Vector4f modelExporter$texUVWH() {
        throw new UnsupportedOperationException();
    }

    default Vector3f modelExporter$getGrow() {
        throw new UnsupportedOperationException();
    }

}
