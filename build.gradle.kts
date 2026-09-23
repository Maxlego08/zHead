plugins {
    java
    id("com.gradleup.shadow") version "9.0.0-beta11"
}

group = "fr.maxlego08.head"
version = "1.5"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven(url = "https://repo.extendedclip.com/releases/")
    maven(url = "https://libraries.minecraft.net/")
    maven(url = "https://repo.papermc.io/repository/maven-public/")
    maven {
        name = "faststatsReleases"
        url = uri("https://repo.faststats.dev/releases")
    }
}

dependencies {
    compileOnly("dev.folia:folia-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("com.mojang:authlib:3.11.50")

    implementation("dev.faststats.metrics:bukkit:0.30.1")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release = 17
    }

    shadowJar {
        relocate("dev.faststats", "fr.maxlego08.head.libs.faststats")

        // Les artefacts FastStats embarquent chacun leur propre META-INF/faststats.properties :
        // on ne garde que la premiere entree.
        filesMatching("META-INF/faststats.properties") {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }

        System.getProperty("github.sha")?.let { sha ->
            archiveClassifier.set("${System.getProperty("archive.classifier")}-${sha}")
        } ?: archiveClassifier.set(System.getProperty("archive.classifier"))
        archiveBaseName.set("zHead")
        archiveVersion.set("")
        destinationDirectory.set(file("target/"))
    }

    build {
        dependsOn(shadowJar)
    }

    processResources {
        val pluginVersion = project.version.toString()
        filesMatching("plugin.yml") {
            expand("version" to pluginVersion)
        }
    }
}
