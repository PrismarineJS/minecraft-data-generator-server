import org.gradle.api.Project
import xyz.wagyourtail.unimined.internal.minecraft.MinecraftProvider
import xyz.wagyourtail.unimined.internal.minecraft.patch.fabric.OfficialFabricMinecraftTransformer
import xyz.wagyourtail.unimined.internal.minecraft.transform.merge.ClassMerger

class CustomOfficialFabricMinecraftTransformer(
    project: Project,
    provider: MinecraftProvider
) : OfficialFabricMinecraftTransformer(project, provider) {
    // Strip @Environment annotation from the classes
    @Suppress("UnstableApiUsage")
    override val merger: ClassMerger = ClassMerger()
}
