package dev.u9g.minecraftdatagenerator.mixin;

import dev.u9g.minecraftdatagenerator.util.DGU;
import dev.u9g.minecraftdatagenerator.util.Registries;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CommonI18n;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

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
