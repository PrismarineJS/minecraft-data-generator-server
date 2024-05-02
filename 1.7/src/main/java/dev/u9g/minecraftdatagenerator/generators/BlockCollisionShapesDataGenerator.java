package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.u9g.minecraftdatagenerator.util.Registries;
import net.minecraft.block.Block;
import net.minecraft.client.Texture;
import net.minecraft.util.math.Box;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlockCollisionShapesDataGenerator implements IDataGenerator {
    private static final Box ENTITY_BOX = Box.of(0.0D, 0.0D, 0.0D, 1.0D, 2.0D, 1.0D);

    private static String nameOf(Block block) {
        return Objects.requireNonNull(Registries.BLOCKS.getId(block));
    }

    private static JsonArray jsonOf(Box box) {
        JsonArray arr = new JsonArray();
        if (box == null) return arr;
        arr.add(new JsonPrimitive(box.minX));
        arr.add(new JsonPrimitive(box.minY));
        arr.add(new JsonPrimitive(box.minZ));
        arr.add(new JsonPrimitive(box.maxX));
        arr.add(new JsonPrimitive(box.maxY));
        arr.add(new JsonPrimitive(box.maxZ));
        return arr;
    }

    @Override
    public String getDataName() {
        return "blockCollisionShapes";
    }

    @Override
    public JsonObject generateDataJson() {
        ShapeCache shapeCache = new ShapeCache();
        JsonObject blocksObject = new JsonObject();

        for (Block block : Registries.BLOCKS) {
            Object val = shapeCache.addShapesFrom(block);
            if (val instanceof JsonArray) {
                blocksObject.add(nameOf(block), (JsonElement) val);
            } else {
                blocksObject.addProperty(nameOf(block), (Integer) val);
            }
        }
        JsonObject resultObject = new JsonObject();
        resultObject.add("blocks", blocksObject);
        resultObject.add("shapes", shapeCache.toJSON());
        return resultObject;
    }

    public static class ShapeCache {
        private final ArrayList<Shapes> shapesCache = new ArrayList<>();

        public Object addShapesFrom(Block block) {
            List<Integer> indexesOfBoxesInTheShapesCache = new ArrayList<>();
            for (Field field : block.getClass().getDeclaredFields()) {
                if (!field.getType().getName().equals("[Lnet.minecraft.client.ItemIcon;")) { // ItemIcon[]
                    continue;
                }
                try {
                    field.setAccessible(true);
                    Texture[] icons = (Texture[]) field.get(block);
                    return icons.length;
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
            // old way
//            new Throwable("find block damages based on ItemIcon[]").printStackTrace();
//            for (BlockState state : block.getStateManager().getBlockStates().reverse()) {
//                List<Box> boxes = new ArrayList<>();
//                try {
//                    block.appendCollisionBoxes(DGU.getWorld(), BlockPos.ORIGIN, state, ENTITY_BOX, boxes, null);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Shapes thisBlockStateShapes = new Shapes(boxes);
//                int indexOfThisBlockStatesShapes = shapesCache.indexOf(thisBlockStateShapes);
//                if (indexOfThisBlockStatesShapes != -1) {
//                    indexesOfBoxesInTheShapesCache.add(indexOfThisBlockStatesShapes);
//                } else {
//                    shapesCache.add(thisBlockStateShapes);
//                    indexesOfBoxesInTheShapesCache.add(shapesCache.size() - 1);
//                }
//            }
//            if (indexesOfBoxesInTheShapesCache.stream().distinct().count() < 2) {
//                return indexesOfBoxesInTheShapesCache.getFirst();
//            } else {
//                JsonArray shapeIndexes = new JsonArray();
//                indexesOfBoxesInTheShapesCache.forEach(shapeIndex -> shapeIndexes.add(new JsonPrimitive(shapeIndex)));
//                return shapeIndexes;
//            }
            return 1;
        }

        public JsonObject toJSON() {
            JsonObject shapes = new JsonObject();
            int i = 0;
            for (Shapes s : shapesCache) {
                shapes.add(String.valueOf(i++), s.toJSON());
            }
            return shapes;
        }

        private record Shapes(List<Box> boxes) {

            public JsonArray toJSON() {
                JsonArray arr = new JsonArray();
                boxes.forEach(box -> arr.add(jsonOf(box)));
                return arr;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Shapes shapes = (Shapes) o;
                return Objects.equals(boxes, shapes.boxes);
            }

        }
    }
}
