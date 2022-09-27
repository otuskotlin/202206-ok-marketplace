buildscript {
    val atomicfuVersion: String by project
    dependencies {
        classpath("org.jetbrains.kotlinx:atomicfu-gradle-plugin:$atomicfuVersion")
    }
}
apply(plugin = "kotlinx-atomicfu")

plugins {
    application
    kotlin("jvm")
    id("com.bmuschko.docker-java-application")
}

application {
    mainClass.set("ru.otus.otuskotlin.marketplace.app.kafka.MainKt")
}

docker {
    javaApplication {
        mainClassName.set(application.mainClass.get())
        baseImage.set("adoptopenjdk/openjdk11:alpine-jre")
        maintainer.set("(c) Otus")
//        ports.set(listOf(8080))
        val imageName = project.name
        images.set(
            listOf(
                "$imageName:${project.version}",
                "$imageName:latest"
            )
        )
        jvmArgs.set(listOf("-Xms256m", "-Xmx512m"))
    }
}

dependencies {
    val kafkaVersion: String by project
    val coroutinesVersion: String by project
    val atomicfuVersion: String by project
    val logbackVersion: String by project
    val kotlinLoggingJvmVersion: String by project
    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:atomicfu:$atomicfuVersion")

    // log
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")

    // transport models
    implementation(project(":ok-marketplace-common"))
    implementation(project(":ok-marketplace-api-v1-jackson"))
    implementation(project(":ok-marketplace-api-v2-kmp"))
    implementation(project(":ok-marketplace-mappers-v1"))
    implementation(project(":ok-marketplace-mappers-v2"))
    // logic
    implementation(project(":ok-marketplace-biz"))

    testImplementation(kotlin("test-junit"))
}
