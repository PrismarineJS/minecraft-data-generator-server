package dev.u9g.minecraftdatagenerator.mixin;

import dev.u9g.minecraftdatagenerator.util.Registries;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Identifier.class)
public class IdentifierMixin {
    @Inject(method = "<init>(Ljava/lang/String;)V", at = @At("TAIL"))
    private void Identifier(String par1, CallbackInfo ci) {
        Registries.IDENTIFIERS.add(par1);
    }
    @Inject(method = "<init>(Ljava/lang/String;Ljava/lang/String;)V", at = @At("TAIL"))
    private void onCreateIdentifier(String path, String par2, CallbackInfo ci) {
        Registries.IDENTIFIERS.add(par2);
    }
}
