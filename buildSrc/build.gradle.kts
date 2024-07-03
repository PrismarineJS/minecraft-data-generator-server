plugins {
    `kotlin-dsl`
}

repositories {
    maven("https://maven.wagyourtail.xyz/releases") {
        name = "WagYourReleases"
    }
    maven("https://maven.wagyourtail.xyz/snapshots") {
        name = "WagYourSnapshots"
    }
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("xyz.wagyourtail.unimined:xyz.wagyourtail.unimined.gradle.plugin:1.2.15-SNAPSHOT")

    // guava
    implementation("com.google.guava:guava:31.1-jre")

    // gson
    implementation("com.google.code.gson:gson:2.9.0")

    // asm
    implementation("org.ow2.asm:asm:9.5")
    implementation("org.ow2.asm:asm-commons:9.5")
    implementation("org.ow2.asm:asm-tree:9.5")
    implementation("org.ow2.asm:asm-analysis:9.5")
    implementation("org.ow2.asm:asm-util:9.5")

    // remapper
    implementation("net.fabricmc:tiny-remapper:0.8.7") {
        exclude(group = "org.ow2.asm")
    }

    // mappings
    implementation("net.fabricmc:mapping-io:0.3.0") {
        exclude(group = "org.ow2.asm")
    }

    // jetbrains annotations
    implementation("org.jetbrains:annotations-java5:23.0.0")

    // binpatcher
    implementation("net.minecraftforge:binarypatcher:1.1.1") {
        exclude(mapOf("group" to "commons-io"))
    }
    implementation("commons-io:commons-io:2.12.0")

    // pack200 provided by apache commons-compress
    implementation("org.apache.commons:commons-compress:1.26.1")

    // aw
    implementation("net.fabricmc:access-widener:2.1.0")
}
