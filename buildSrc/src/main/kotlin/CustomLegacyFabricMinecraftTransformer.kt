import org.gradle.api.Project
import xyz.wagyourtail.unimined.internal.minecraft.MinecraftProvider
import xyz.wagyourtail.unimined.internal.minecraft.patch.fabric.LegacyFabricMinecraftTransformer
import xyz.wagyourtail.unimined.internal.minecraft.transform.merge.ClassMerger

class CustomLegacyFabricMinecraftTransformer(
    project: Project,
    provider: MinecraftProvider
) : LegacyFabricMinecraftTransformer(project, provider) {
    // Strip @Environment annotation from the classes
    @Suppress("UnstableApiUsage")
    override val merger: ClassMerger = ClassMerger()
}
