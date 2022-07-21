rootProject.name = "marketplace"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings

        kotlin("jvm") version kotlinVersion apply false
    }
}

include("m1l1-hello-world")
include("m1l3-oop")
include("m1l4-dsl")
include("m1l6-flows-and-channels")
