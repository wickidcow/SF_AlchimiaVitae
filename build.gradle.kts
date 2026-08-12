plugins {
    `java-library`
    id("com.gradleup.shadow") version "8.3.6"
    id("xyz.jpenilla.run-paper") version "3.0.2"
}

group = "me.apeiros"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://api.modrinth.com/maven")
    maven("https://jitpack.io")
    maven("https://repo.codemc.org/repository/maven-public")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("maven.modrinth:slimefuncore:PEuZoZh4")
    compileOnly(fileTree("run/plugins") { include("*.jar") })
    compileOnly(fileTree("libs") { include("*.jar") })

    // JSR305
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
    archiveFileName.set("SF_AlchimiaVitae_Legacy_v${project.version}.jar")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.runServer {
    minecraftVersion("1.21.4")
}
