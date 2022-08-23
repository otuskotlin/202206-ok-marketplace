@file:Suppress("UNUSED_VARIABLE")

import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
import com.bmuschko.gradle.docker.tasks.image.Dockerfile

val ktorVersion: String by project

plugins {
    kotlin("multiplatform")
    id("com.bmuschko.docker-remote-api")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

kotlin {
    val nativeTarget = when (System.getProperty("os.name")) {
        "Mac OS X" -> macosX64("native")
        "Linux" -> linuxX64("native")
        // Windows is currently not supported
        // Other supported targets are listed here: https://ktor.io/docs/native-server.html#targets
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "ru.otus.otuskotlin.marketplace.main"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":ok-marketplace-common"))
                implementation(project(":ok-marketplace-api-v2-kmp"))
                implementation(project(":ok-marketplace-mappers-v2"))

                implementation(project(":ok-marketplace-app-ktor-common"))
            }
        }
        val nativeMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-core:$ktorVersion")
                implementation("io.ktor:ktor-server-cio:$ktorVersion")

                implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
            }
        }
        val nativeTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

tasks {
    val linkReleaseExecutableNative by getting(org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink::class) {

    }
    val dockerDockerfile by creating(Dockerfile::class) {
        group = "docker"
        from("ubuntu:22.02")
        doFirst {
            copy {
                from(linkReleaseExecutableNative.binary.outputFile)
                into("${this@creating.temporaryDir}/app")
            }
        }
        copyFile("app", "/app")
        entryPoint("/app")
    }
    @Suppress("UNUSED_VARIABLE")
    val dockerBuildImage by creating(DockerBuildImage::class) {
        group = "docker"
        dependsOn(dockerDockerfile)
        images.add("${project.name}:${project.version}")
    }
}
