package dev.u9g.minecraftdatagenerator.mixin.overwrite;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

@Mixin(EndPortalFrameBlock.class)
public class EndPortalFrameBlockOverwrite extends Block {
    protected EndPortalFrameBlockOverwrite(Material material) {
        super(material);
    }

    /**
     * @author a
     * @reason a
     */
    @Overwrite()
    public void appendCollisionBoxes(World world, BlockPos pos, BlockState state, Box box, List<Box> list, Entity entity) {
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
        super.appendCollisionBoxes(world, pos, state, box, list, entity);
        this.setBoundingBox(0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
        super.appendCollisionBoxes(world, pos, state, box, list, entity);
        this.setBlockItemBounds();
    }
}
