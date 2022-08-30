plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
}

kotlin {
    jvm {}

    sourceSets {

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(project(":ok-marketplace-common"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}
