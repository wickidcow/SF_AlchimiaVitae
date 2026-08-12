import org.gradle.api.attributes.java.TargetJvmVersion

plugins {
    java
}

group = "me.apeiros"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.2.build.+") {
        attributes {
            attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 25)
        }
    }
    compileOnly("com.github.slimefun:Slimefun:Legacy-SNAPSHOT")
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(21)
}

tasks.processResources {
    val pluginVersion = project.version.toString()
    inputs.property("version", pluginVersion)
    filesMatching("plugin.yml") {
        expand(mapOf("version" to pluginVersion))
    }
}

tasks.jar {
    archiveFileName.set("SF_AlchimiaVitae_Legacy_v${project.version}.jar")
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
    from("LICENSE") {
        into("META-INF")
    }
}
