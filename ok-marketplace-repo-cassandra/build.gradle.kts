plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    val coroutinesVersion: String by project
    val cassandraDriverVersion: String by project
    val testContainersVersion: String by project
    val logbackVersion: String by project
    val kotlinLoggingJvmVersion: String by project
    val kmpUUIDVersion: String by project

    implementation(project(":ok-marketplace-common"))

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutinesVersion")

    implementation("com.benasher44:uuid:$kmpUUIDVersion")

    implementation("com.datastax.oss:java-driver-core:$cassandraDriverVersion")
    implementation("com.datastax.oss:java-driver-query-builder:$cassandraDriverVersion")
    kapt("com.datastax.oss:java-driver-mapper-processor:$cassandraDriverVersion")
    implementation("com.datastax.oss:java-driver-mapper-runtime:$cassandraDriverVersion")

    // log
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")

    testImplementation(project(":ok-marketplace-repo-tests"))
    testImplementation("org.testcontainers:cassandra:$testContainersVersion")
}
