package dev.u9g.minecraftdatagenerator.mixin;

import dev.u9g.minecraftdatagenerator.util.Registries;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Blocks.class)
public class BlocksMixin {
    @Inject(method = "<clinit>", at = @At("HEAD"))
    private static void doBlocksStaticCtor(CallbackInfo ci) {
//        Block.setup();
    }
}
