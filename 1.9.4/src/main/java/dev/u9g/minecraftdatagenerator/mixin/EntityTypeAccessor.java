package dev.u9g.minecraftdatagenerator.mixin;

import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(EntityType.class)
public interface EntityTypeAccessor {
    @Accessor("NAME_CLASS_MAP")
    static Map<String, Class<? extends Entity>> NAME_CLASS_MAP(){throw new Error();}
    @Accessor("CLASS_NAME_MAP")
    static Map<Class<? extends Entity>, String> CLASS_NAME_MAP(){throw new Error();}
    @Accessor("ID_CLASS_MAP")
    static Map<Integer, Class<? extends Entity>> ID_CLASS_MAP(){throw new Error();}
    @Accessor("CLASS_ID_MAP")
    static Map<Class<? extends Entity>, Integer> CLASS_ID_MAP(){throw new Error();}
    @Accessor("NAME_ID_MAP")
    static Map<String, Integer> NAME_ID_MAP(){throw new Error();}
}
