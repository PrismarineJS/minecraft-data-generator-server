import org.gradle.api.Project
import xyz.wagyourtail.unimined.api.minecraft.EnvType
import xyz.wagyourtail.unimined.api.minecraft.MinecraftJar
import xyz.wagyourtail.unimined.internal.minecraft.MinecraftProvider
import xyz.wagyourtail.unimined.internal.minecraft.patch.fabric.LegacyFabricMinecraftTransformer

class CustomLegacyFabricMinecraftTransformer(
    project: Project,
    provider: MinecraftProvider
) : LegacyFabricMinecraftTransformer(project, provider) {
    @Suppress("UnstableApiUsage")
    override fun mergedJar(clientjar: MinecraftJar, serverjar: MinecraftJar): MinecraftJar {
        return MinecraftJar(
            clientjar,
            envType = EnvType.COMBINED,
            patches = listOf("$providerName-merged", "custom-no-environment") + clientjar.patches + serverjar.patches
        )
    }
}
