package dev.u9g.minecraftdatagenerator.mixin.accessor;

import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(Language.class)
public interface LanguageAccessor {
    @Accessor("translations")
    Map<String, String> translations();
}
