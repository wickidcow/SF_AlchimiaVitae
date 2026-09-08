plugins {
    `java-library`
    id("com.gradleup.shadow") version "8.3.6"
    id("xyz.jpenilla.run-paper") version "3.0.2"
}

group = "me.apeiros"
version = "1.0.3"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://api.modrinth.com/maven")
    maven("https://jitpack.io")
    maven("https://repo.codemc.org/repository/maven-public")
}

val legacyJar = file("legacy-deps/Slimefun-Legacy4.1.46.jar")

dependencies {
    // Keep the release build Java 21 compatible. Paper 26.2 is verified separately
    // by the Maven compatibility gate running on JDK 25.
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")

    // CI and release builds use the exact Slimefun Legacy release JAR.
    // Keep the Gugu coordinate only as a local-development fallback when that JAR is absent.
    if (legacyJar.exists()) {
        compileOnly(files(legacyJar))
    } else {
        compileOnly("com.github.SlimefunGuguProject:Slimefun4:2025.1")
    }

    compileOnly(fileTree("run/plugins") { include("*.jar") })
    compileOnly(fileTree("libs") { include("*.jar") })
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(21)
}

tasks.processResources {
    val props = mapOf(
        "version" to project.version,
        "project" to mapOf("version" to project.version)
    )
    inputs.properties(props)
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.shadowJar {
    archiveClassifier.set("")
    archiveFileName.set("SF_AlchimiaVitae${project.version}.jar")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.runServer {
    minecraftVersion("1.21.11")
}
