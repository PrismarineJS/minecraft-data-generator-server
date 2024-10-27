package dev.u9g.minecraftdatagenerator.mixin.overwrite;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ItemEntity.class)
public abstract class ItemEntityOverwrite extends Entity {

    public ItemEntityOverwrite(World world) {
        super(world);
    }

    /**
     * @author a
     * @reason a
     */
    @Overwrite
    public String getTranslationKey() {
        return super.getTranslationKey();
    }
}
