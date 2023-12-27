package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.u9g.minecraftdatagenerator.mixin.accessor.LanguageAccessor;
import dev.u9g.minecraftdatagenerator.util.Registries;
import net.minecraft.block.Material;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.entity.NoteBlockBlockEntity;
import net.minecraft.item.MusicDiscItem;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class InstrumentsDataGenerator implements IDataGenerator {
    public static Material currentMaterial = null;
    public static int LAST_WORLD_BLOCKDATA = -1;
    @Override
    public String getDataName() {
        return "instruments";
    }

    @Override
    public JsonElement generateDataJson() {
        JsonArray array = new JsonArray();
        // copied from NoteBlock#syncedBlockAction
        array.add(makeInstrumentObj(0, "harp"));
        array.add(makeInstrumentObj(1, "bd"));
        array.add(makeInstrumentObj(2, "snare"));
        array.add(makeInstrumentObj(3, "hat"));
        array.add(makeInstrumentObj(4, "bassattack"));
        return array;
    }

    private JsonObject makeInstrumentObj(int i, String s) {
        JsonObject obj = new JsonObject();
        obj.add("id", new JsonPrimitive(i));
        obj.add("name", new JsonPrimitive(s));
        return obj;
    }
}
