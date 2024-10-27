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
    implementation("io.freefair.gradle:lombok-plugin:8.10.2")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.25.0")
}
