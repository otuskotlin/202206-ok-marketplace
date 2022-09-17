plugins {
    kotlin("jvm")
}

val coroutinesVersion: String by project

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))

    testImplementation("io.konform:konform:0.4.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}
