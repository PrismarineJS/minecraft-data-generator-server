import xyz.wagyourtail.unimined.api.minecraft.MinecraftConfig
import xyz.wagyourtail.unimined.api.minecraft.task.RemapJarTask
import xyz.wagyourtail.unimined.internal.minecraft.MinecraftProvider

plugins {
    id("dg-java-conventions")
    id("xyz.wagyourtail.unimined")
}

dependencies {
    implementation(project(":common"))
}

tasks.withType<RemapJarTask> {
    onlyIf { false }
}
