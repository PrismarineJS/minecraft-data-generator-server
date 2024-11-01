plugins {
    idea
    `java-library`
    id("io.freefair.lombok")
    id("com.diffplug.spotless")
}

spotless {
    java {
        trimTrailingWhitespace()
        indentWithSpaces(4)
        endWithNewline()

        importOrder("", "javax|java", "\\#")
    }
}

afterEvaluate {
    tasks.processResources {
        filteringCharset = "UTF-8"

        filesMatching("fabric.mod.json") {
            expand("version" to rootProject.version)
        }
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

