
plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

dependencies {
    implementation("com.yandex.cloud:java-sdk-functions:2.5.1")
    implementation(kotlin("stdlib-jdk8"))

    val jacksonVersion: String by project
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    // transport models
    implementation(project(":ok-marketplace-common"))
    implementation(project(":ok-marketplace-api-v1-jackson"))
    implementation(project(":ok-marketplace-api-v2-kmp"))
    implementation(project(":ok-marketplace-mappers-v1"))

    // v2 api
    implementation(project(":ok-marketplace-mappers-v2"))

    // Stubs
    implementation(project(":ok-marketplace-stubs"))
}