import org.gradle.api.Project
import xyz.wagyourtail.unimined.api.minecraft.EnvType
import xyz.wagyourtail.unimined.api.minecraft.MinecraftJar
import xyz.wagyourtail.unimined.internal.minecraft.MinecraftProvider
import xyz.wagyourtail.unimined.internal.minecraft.patch.fabric.OfficialFabricMinecraftTransformer
import xyz.wagyourtail.unimined.internal.minecraft.transform.merge.ClassMerger

// Prevents adding @Environment annotations to classes
@Suppress("UnstableApiUsage")
class CustomOfficialFabricMinecraftTransformer(
    project: Project,
    provider: MinecraftProvider
) : OfficialFabricMinecraftTransformer(project, provider) {
    override val merger: ClassMerger = ClassMerger()

    override fun mergedJar(clientjar: MinecraftJar, serverjar: MinecraftJar): MinecraftJar {
        return MinecraftJar(
            clientjar,
            envType = EnvType.COMBINED,
            patches = listOf("$providerName-merged", "custom-no-environment") + clientjar.patches + serverjar.patches
        )
    }
}
