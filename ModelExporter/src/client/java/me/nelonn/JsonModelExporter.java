package me.nelonn;

import com.google.common.math.DoubleMath;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import me.nelonn.accessor.CubeAccessor;
import me.nelonn.accessor.EntityModelSetAccessor;
import me.nelonn.accessor.LayerDefinitionAccessor;
import me.nelonn.accessor.PoseAccessor;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.core.Direction;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

public class JsonModelExporter {

    public static void export(EntityModelSet entityModelSet) {
        float global_scale = 0.5F;
        boolean snap_angles = true; // make angles snap by 22.5

        System.out.println("Exporting Models");
        Path dir = Path.of("./models");
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        EntityModelSetAccessor entityModelSetAccessor = (EntityModelSetAccessor) (Object) entityModelSet;
        ModelLayers.getKnownLocations().forEach(modelLayerLocation -> {
            PoseStack poseStack = new PoseStack();
            JsonObject rootObject = new JsonObject();
            rootObject.addProperty("credit", "Model Exporter by Nelonn");
            JsonArray elements = new JsonArray();
            if (modelLayerLocation.getModel().getPath().equals("ghast") && modelLayerLocation.getLayer().equals("main")) {
                System.out.println("DEBUG POINT");
            }
            LayerDefinition layerDefinition = entityModelSetAccessor.modelExporter$getRoots().get(modelLayerLocation);
            Vector2i textureSize = ((LayerDefinitionAccessor) (Object) layerDefinition).modelExporter$getMaterial();
            rootObject.add("texture_size", intsToArray(textureSize.x, textureSize.y));
            layerDefinition.bakeRoot().visit(poseStack, (PoseStack.Pose pose, String path, int cubeIndex, ModelPart.Cube cube) -> {
                CubeAccessor cubeAccessor = (CubeAccessor) (Object) cube;

                // Used as workaround for IntelliJ IDEA issue
                PoseAccessor poseAccessor = new Function<PoseStack.Pose, PoseAccessor>() {
                    @Override
                    public PoseAccessor apply(PoseStack.Pose pose) {
                        return (PoseAccessor) (Object) pose;
                    }
                }.apply(pose);

                Vector3f from = new Vector3f(cube.minX, cube.minY, cube.minZ).sub(cubeAccessor.modelExporter$getGrow()).div(16.0F);
                Vector3f to = new Vector3f(cube.maxX, cube.maxY, cube.maxZ).add(cubeAccessor.modelExporter$getGrow()).div(16.0F);
                from.add(poseAccessor.modelExporter$translation()).mul(poseAccessor.modelExporter$scale());
                to.add(poseAccessor.modelExporter$translation()).mul(poseAccessor.modelExporter$scale());
                from.mul(16.0F * global_scale);
                to.mul(16.0F * global_scale);
                float from_y = from.y;
                from.y = to.y * -1;
                to.y = from_y * -1;

                // Center model
                from.add(8.0F, 12.0F, 8.0F);
                to.add(8.0F, 12.0F, 8.0F);

                Vector3f euler = new Vector3f();
                poseAccessor.modelExporter$rotation().getEulerAnglesXYZ(euler);
                euler.mul((float) (180.0D / Math.PI));

                float angle = 0.0F;
                Direction.Axis axis = Direction.Axis.Y;
                if (euler.x != 0 && DoubleMath.fuzzyEquals(euler.y, 0, 1.0E-7) && DoubleMath.fuzzyEquals(euler.z, 0, 1.0E-7)) {
                    angle = -euler.x;
                    axis = Direction.Axis.X;
                } else if (DoubleMath.fuzzyEquals(euler.x, 0, 1.0E-7) && euler.y != 0 && DoubleMath.fuzzyEquals(euler.z, 0, 1.0E-7)) {
                    angle = -euler.y;
                    axis = Direction.Axis.Y;
                } else if (DoubleMath.fuzzyEquals(euler.x, 0, 1.0E-7) && DoubleMath.fuzzyEquals(euler.y, 0, 1.0E-7) && euler.z != 0) {
                    angle = -euler.z;
                    axis = Direction.Axis.Z;
                }
                if (snap_angles) {
                    angle = Math.round(angle / 22.5F) * 22.5F;
                }

                JsonObject element = new JsonObject();
                element.addProperty("name", path);
                element.add("from", floatsToArray(from.x, from.y, from.z));
                element.add("to", floatsToArray(to.x, to.y, to.z));
                element.add("rotation", createRotation(angle, axis, new Vector3f()
                        .add(poseAccessor.modelExporter$translation())
                        .mul(16.0F * global_scale)
                        .mul(1, -1, 1)
                        .add(8, 12, 8))); // Center model
                element.add("faces", createFaces(textureSize, cube));
                elements.add(element);
            });
            rootObject.add("elements", elements);
            String fileName = modelLayerLocation.getModel().getPath() + "-" + modelLayerLocation.getLayer() + ".json";
            Path file = dir.resolve(fileName);
            try {
                Files.writeString(file, rootObject.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Exported " + fileName);
        });
    }



    private static JsonObject createRotation(float angle, Direction.Axis axis, Vector3f origin) {
        JsonObject rotation = new JsonObject();
        rotation.addProperty("angle", angle);
        rotation.addProperty("axis", axis.toString());
        rotation.add("origin", floatsToArray(origin.x, origin.y, origin.z));
        return rotation;
    }

    private static JsonObject createFaces(Vector2i textureSize, ModelPart.Cube cube) {
        CubeAccessor cubeAccessor = (CubeAccessor) (Object) cube;
        Vector4f uvwh = cubeAccessor.modelExporter$texUVWH();
        float xSize = cube.maxX - cube.minX;
        float ySize = cube.maxY - cube.minY;
        float zSize = cube.maxZ - cube.minZ;
        // The values are obtained by many hours of iterations
        float scaleX = 0.25F / (((float) textureSize.x) / 64.0F);
        float scaleY = 0.25F / (((float) textureSize.y) / 64.0F);
        Vector4f scale = new Vector4f(scaleX, scaleY, scaleX, scaleY);
        Vector2f offset = new Vector2f(uvwh.x, uvwh.y).mul(scaleX, scaleY);
        JsonObject faces = new JsonObject();
        faces.add("north", createFace(offset, 0,   new Vector4f(   zSize,                 zSize,  xSize, ySize).mul(scale)));
        faces.add("east",  createFace(offset, 0,   new Vector4f(0,                     zSize,  zSize, ySize).mul(scale)));
        faces.add("south", createFace(offset, 0,   new Vector4f(zSize + xSize + zSize, zSize,  xSize, ySize).mul(scale)));
        faces.add("west",  createFace(offset, 0,   new Vector4f(zSize + xSize,         zSize,  zSize, ySize).mul(scale)));
        faces.add("up",    createFace(offset, 180, new Vector4f(   zSize,                 0,   xSize, zSize).mul(scale)));
        faces.add("down",  createFace(offset, 0,   new Vector4f(zSize + xSize,         0,   xSize, zSize).mul(scale)));
        return faces;
    }

    private static JsonObject createFace(Vector2f offset, int rotation, Vector4f uvwh) {
        JsonObject face = new JsonObject();
        JsonArray uv = new JsonArray(4);
        uv.add(offset.x + uvwh.x);
        uv.add(offset.y + uvwh.y);
        uv.add(offset.x + uvwh.x + uvwh.z);
        uv.add(offset.y + uvwh.y + uvwh.w);
        face.add("uv", uv);
        if (rotation != 0) {
            face.addProperty("rotation", rotation);
        }
        face.addProperty("texture", "#missing");
        return face;
    }

    private static JsonArray floatsToArray(float... floats) {
        JsonArray array = new JsonArray(floats.length);
        for (float value : floats) {
            array.add(value);
        }
        return array;
    }

    private static JsonArray intsToArray(int... ints) {
        JsonArray array = new JsonArray(ints.length);
        for (int value : ints) {
            array.add(value);
        }
        return array;
    }

}
