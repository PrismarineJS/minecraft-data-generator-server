package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.block.NoteBlock;
import net.minecraft.sound.Sound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(NoteBlock.class)
public interface NoteBlockAccessor {
    @Accessor("TUNES")
    static List<Sound> TUNES() {
        return null;
    }
}
