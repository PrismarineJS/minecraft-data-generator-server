package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.block.NoteBlock;
import net.minecraft.client.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(NoteBlock.class)
public interface NoteBlockAccessor {
    @Accessor("TUNES")
    public static List<SoundEvent> TUNES() {
        return null;
    }
}
