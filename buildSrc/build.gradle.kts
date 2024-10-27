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
}
