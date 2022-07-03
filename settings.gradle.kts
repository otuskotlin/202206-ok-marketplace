rootProject.name = "marketplace"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings

        kotlin("jvm") version kotlinVersion
    }
}

include("m1l1-hello-world")
include("m1l3-oop")
