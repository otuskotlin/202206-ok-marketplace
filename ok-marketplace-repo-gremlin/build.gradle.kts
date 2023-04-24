plugins {
    kotlin("jvm")
}

dependencies {
    val arcadeDbVersion: String by project
    val tinkerpopVersion: String by project
    val coroutinesVersion: String by project
    val kmpUUIDVersion: String by project
    val testContainersVersion: String by project

    implementation(project(":ok-marketplace-common"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("com.benasher44:uuid:$kmpUUIDVersion")

    testImplementation(project(":ok-marketplace-repo-tests"))
    implementation("org.slf4j:slf4j-api:2.0.6")

    implementation(kotlin("stdlib-jdk8"))

    implementation("com.arcadedb:arcadedb-engine:$arcadeDbVersion")
    implementation("com.arcadedb:arcadedb-network:$arcadeDbVersion")
    implementation("com.arcadedb:arcadedb-gremlin:$arcadeDbVersion")
    implementation("com.arcadedb:arcadedb-postgresw:$arcadeDbVersion")
    implementation("com.arcadedb:arcadedb-server:$arcadeDbVersion")
    implementation("org.apache.tinkerpop:gremlin-core:$tinkerpopVersion")
    implementation("org.apache.tinkerpop:gremlin-server:$tinkerpopVersion")
    testImplementation(kotlin("test-junit"))
    testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
}
