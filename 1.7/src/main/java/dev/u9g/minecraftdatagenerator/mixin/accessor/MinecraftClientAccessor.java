package dev.u9g.minecraftdatagenerator.mixin.accessor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.server.integrated.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {
    @Accessor("server")
    IntegratedServer getServer();
}
