plugins {
    kotlin("jvm")
}

dependencies {
    val rabbitVersion: String by project
    val jacksonVersion: String by project
    val logbackVersion: String by project
    val coroutinesVersion: String by project
    val testContainersVersion: String by project

    implementation(kotlin("stdlib"))
    implementation("com.rabbitmq:amqp-client:$rabbitVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    // transport models common
    implementation(project(":ok-marketplace-common"))

    // v1 api
    implementation(project(":ok-marketplace-api-v1-jackson"))
    implementation(project(":ok-marketplace-mappers-v1"))

    // v2 api
    implementation(project(":ok-marketplace-mappers-v2"))
    implementation(project(":ok-marketplace-api-v2-kmp"))

    // Services
    implementation(project(":ok-marketplace-biz"))

    // Stubs
    implementation(project(":ok-marketplace-stubs"))

    testImplementation("org.testcontainers:rabbitmq:$testContainersVersion")
    testImplementation(kotlin("test"))
}
