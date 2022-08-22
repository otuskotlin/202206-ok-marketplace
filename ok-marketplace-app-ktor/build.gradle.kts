import org.jetbrains.kotlin.util.suffixIfNot

val ktorVersion: String by project

// ex: Converts to "io.ktor:ktor-ktor-server-netty:2.0.1" with only ktor("netty")
fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("application")
    id("com.bmuschko.docker-java-application")
}

application {
//    mainClass.set("io.ktor.server.netty.EngineMain")
    mainClass.set("ru.otus.otuskotlin.marketplace.app.ApplicationKt")
}

docker {
    javaApplication {
        mainClassName.set(application.mainClass.get())
        baseImage.set("adoptopenjdk/openjdk17:alpine-jre")
        maintainer.set("(c) Otus")
        ports.set(listOf(8080))
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
    val logbackVersion: String by project
    val kotlinLoggingJvmVersion: String by project

    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"
    implementation(ktor("netty")) // "io.ktor:ktor-ktor-server-netty:$ktorVersion"

    // jackson
    implementation(ktor("jackson", "serialization")) // io.ktor:ktor-serialization-jackson
    implementation(ktor("content-negotiation")) // io.ktor:ktor-server-content-negotiation

    implementation(ktor("locations"))
    implementation(ktor("caching-headers"))
    implementation(ktor("call-logging"))
    implementation(ktor("auto-head-response"))
    implementation(ktor("cors")) // "io.ktor:ktor-cors:$ktorVersion"
    implementation(ktor("default-headers")) // "io.ktor:ktor-cors:$ktorVersion"
    implementation(ktor("cors")) // "io.ktor:ktor-cors:$ktorVersion"
    implementation(ktor("auto-head-response"))
    implementation(ktor("websockets"))

    implementation(ktor("websockets")) // "io.ktor:ktor-websockets:$ktorVersion"
    implementation(ktor("auth")) // "io.ktor:ktor-auth:$ktorVersion"
    implementation(ktor("auth-jwt")) // "io.ktor:ktor-auth-jwt:$ktorVersion"

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")

    // transport models
    implementation(project(":ok-marketplace-common"))
    implementation(project(":ok-marketplace-app-ktor-common"))
    implementation(project(":ok-marketplace-api-v1-jackson"))
    implementation(project(":ok-marketplace-api-v2-kmp"))
    implementation(project(":ok-marketplace-mappers-v1"))

    // v2 api
    implementation(project(":ok-marketplace-mappers-v2"))

    // Services
//    implementation(project(":ok-marketplace-services"))

    // Stubs
    implementation(project(":ok-marketplace-stubs"))

    // Repositories
//    implementation(project(":ok-marketplace-repo-inmemory"))

    testImplementation(kotlin("test-junit"))
    testImplementation(ktor("test-host")) // "io.ktor:ktor-server-test-host:$ktorVersion"
    testImplementation(ktor("content-negotiation", prefix = "client-"))
    testImplementation(ktor("websockets", prefix = "client-"))
}
