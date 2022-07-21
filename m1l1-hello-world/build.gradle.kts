plugins {
    application
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

application {
    mainClass.set("ru.otus.otuskotlin.marketplace.AppKt")
}
