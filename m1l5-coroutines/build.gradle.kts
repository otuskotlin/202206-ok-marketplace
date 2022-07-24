plugins {
    kotlin("jvm")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3") // http client
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1") // from string to object

    testImplementation(kotlin("test-junit"))
}
