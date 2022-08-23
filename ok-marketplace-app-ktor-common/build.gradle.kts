@file:Suppress("UNUSED_VARIABLE")

import org.jetbrains.kotlin.util.suffixIfNot

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

val ktorVersion: String by project
val serializationVersion: String by project

fun ktor(
    module: String,
    prefix: String = "server-",
    version: String? = this@Build_gradle.ktorVersion,
): Any = "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

kotlin {
    jvm { }
    macosX64 {}
    linuxX64 {}

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"

                implementation(project(":ok-marketplace-common"))
                implementation(project(":ok-marketplace-api-v2-kmp"))
                implementation(project(":ok-marketplace-mappers-v2"))
                implementation(project(":ok-marketplace-stubs"))

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
