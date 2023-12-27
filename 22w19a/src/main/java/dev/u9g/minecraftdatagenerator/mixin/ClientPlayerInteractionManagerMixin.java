package dev.u9g.minecraftdatagenerator.mixin;

import com.google.common.base.Preconditions;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    @Unique
    private long blockBreakingStartTicks;
    @Unique
    private BlockState stateBeingBroken;

    @Inject(method = "attackBlock", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;currentBreakingProgress:F", opcode = Opcodes.PUTFIELD))
    private void onBlockAttacked(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> callbackInfo) {
        World world = Preconditions.checkNotNull(MinecraftClient.getInstance().world);
        this.blockBreakingStartTicks = world.getTime();
    }

    @Inject(method = "cancelBlockBreaking", at = @At("HEAD"))
    private void onBlockBreakingCanceled(CallbackInfo callbackInfo) {
        this.blockBreakingStartTicks = -1L;
    }

    @Inject(method = "breakBlock", at = @At("HEAD"))
    private void onBreakBlockHead(BlockPos pos, CallbackInfoReturnable<Boolean> callbackInfo) {
        World world = Preconditions.checkNotNull(MinecraftClient.getInstance().world);
        this.stateBeingBroken = world.getBlockState(pos);
    }

    @Inject(method = "breakBlock", at = @At("RETURN"))
    private void onBreakBlockReturn(BlockPos pos, CallbackInfoReturnable<Boolean> callbackInfo) {
        World world = Preconditions.checkNotNull(MinecraftClient.getInstance().world);
        if (callbackInfo.getReturnValue()) {
            Preconditions.checkNotNull(stateBeingBroken);
            if (blockBreakingStartTicks != -1L) {
                long totalTicksElapsed = world.getTime() - this.blockBreakingStartTicks;
                long totalTimeElapsed = totalTicksElapsed * 50L;
                System.out.printf("Breaking block %s took %dms%n", stateBeingBroken.getBlock(), totalTimeElapsed);
            } else {
                System.out.printf("Breaking block %s took 0ms [INSTANT BREAK]%n", stateBeingBroken.getBlock());
            }
        }
        this.blockBreakingStartTicks = -1L;
    }
}
