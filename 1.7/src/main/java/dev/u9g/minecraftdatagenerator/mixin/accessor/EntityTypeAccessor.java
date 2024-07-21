package dev.u9g.minecraftdatagenerator.mixin.accessor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(EntityType.class)
public interface EntityTypeAccessor {
    @Accessor("CLASS_NAME_MAP")
    static Map<Class<? extends Entity>, String> CLASS_NAME_MAP() {
        throw new IllegalStateException();
    }

    @Accessor("ID_CLASS_MAP")
    static Map<Integer, Class<? extends Entity>> ID_CLASS_MAP() {
        throw new IllegalStateException();
    }

    @Accessor("CLASS_ID_MAP")
    static Map<Class<? extends Entity>, Integer> CLASS_ID_MAP() {
        throw new IllegalStateException();
    }
}
