plugins {
    kotlin("multiplatform")
}



kotlin {
    jvm {}
    macosX64 {}
    linuxX64 {}

    sourceSets {
        val cache4kVersion: String by project
        val coroutinesVersion: String by project
        val kmpUUIDVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(project(":ok-marketplace-common"))

                implementation("io.github.reactivecircus.cache4k:cache4k:$cache4kVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("com.benasher44:uuid:$kmpUUIDVersion")
                implementation(project(":ok-marketplace-repo-tests"))

            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
