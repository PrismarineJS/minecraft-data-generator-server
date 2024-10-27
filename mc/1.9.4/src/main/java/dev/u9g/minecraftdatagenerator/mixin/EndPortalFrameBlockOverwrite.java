package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

@Mixin(EndPortalFrameBlock.class)
public class EndPortalFrameBlockOverwrite {
    /**
     * @author a
     * @reason a
     */
    @Overwrite()
    public void appendCollisionBoxes(BlockState state, World world, BlockPos pos, Box entityBox, List<Box> boxes, @Nullable Entity entity) {
        boxes.add(EndPortalFrameBlockAccessor.portalFrame());
        boxes.add(EndPortalFrameBlockAccessor.eye());
    }
}
