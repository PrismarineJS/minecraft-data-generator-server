package me.nelonn.accessor;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public interface PoseAccessor {

    default Vector3f modelExporter$translation() {
        throw new UnsupportedOperationException();
    }

    default Vector3f modelExporter$scale() {
        throw new UnsupportedOperationException();
    }

    default Quaternionf modelExporter$rotation() {
        throw new UnsupportedOperationException();
    }

}
