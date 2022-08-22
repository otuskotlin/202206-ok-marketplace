@file:Suppress("UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {}
    macosX64 {}
    linuxX64 {}

    sourceSets {
        all { languageSettings.optIn("kotlin.RequiresOptIn") }

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                // transport models
                implementation(project(":ok-marketplace-common"))
                implementation(project(":ok-marketplace-stubs"))
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
