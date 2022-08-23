rootProject.name = "ok-202206-marketplace"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val kotestVersion: String by settings
        val openapiVersion: String by settings
        val springframeworkBootVersion: String by settings
        val springDependencyManagementVersion: String by settings
        val pluginSpringVersion: String by settings
        val pluginJpa: String by settings

        kotlin("jvm") version kotlinVersion apply false
        kotlin("multiplatform") version kotlinVersion apply false
        id("io.kotest.multiplatform") version kotestVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.springframework.boot") version springframeworkBootVersion apply false
        id("io.spring.dependency-management") version springDependencyManagementVersion apply false
        kotlin("plugin.spring") version pluginSpringVersion apply false
        kotlin("plugin.jpa") version pluginJpa apply false

        id("org.openapi.generator") version openapiVersion apply false

    }
}

//include("m1l1-hello-world")
//include("m1l3-oop")
//include("m1l4-dsl")
//include("m1l5-coroutines")
//include("m1l6-flows-and-channels")
//include("m1l7-kmp")
//include("m2l2-testing")
include("m3l1-spring")

include("ok-marketplace-api-v1-jackson")
include("ok-marketplace-api-v2-kmp")
include("ok-marketplace-common")
include("ok-marketplace-mappers-v1")
include("ok-marketplace-mappers-v2")
include ("ok-marketplace-stubs")
include ("ok-marketplace-app-spring")
include("ok-marketplace-app-serverless")
