import com.github.gradle.node.npm.task.NpmTask

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project


plugins {
    application
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.11"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
    id("com.github.node-gradle.node") version "7.0.2"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "icu.takeneko"
version = "1.0.0"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auto-head-response-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-cors-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-caching-headers-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
}
val yarnBuild = task<NpmTask>("yarnBuild") {
    workingDir = file("frontend/comprehension")
    args.set(listOf("run", "build"))
}

val copyDistFolder = tasks.register<Copy>("copyDistFolder") {
    from(file("frontend/comprehension/dist"))
    into(file("src/main/resources/dist"))
}

var env = System.getProperty("env") ?: "production"

tasks.processResources {
    outputs.upToDateWhen { false }
    filesMatching("*.conf") {
        when (env) {
            "development" -> {
                expand(
                    "KTOR_ENV" to "dev",
                    "KTOR_PORT" to "8081",
                    "KTOR_MODULE" to "build",
                    "KTOR_AUTORELOAD" to "true"
                )
            }

            "production" -> {
                expand(
                    "KTOR_ENV" to "production",
                    "KTOR_PORT" to "80",
                    "KTOR_MODULE" to "",
                    "KTOR_AUTORELOAD" to "false"
                )
            }
        }
    }
}

val setDev = tasks.register("setDev") {
    env = "development"
}

tasks {
    "run" {
        dependsOn(setDev)
    }
    "copyDistFolder" {
        dependsOn(yarnBuild)
    }
    "processResources" {
        dependsOn(copyDistFolder)
    }
}
