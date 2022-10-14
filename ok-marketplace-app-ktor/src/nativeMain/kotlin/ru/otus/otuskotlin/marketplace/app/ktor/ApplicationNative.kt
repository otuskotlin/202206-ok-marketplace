package ru.otus.otuskotlin.marketplace.app.ktor

import io.ktor.server.cio.*
import io.ktor.server.config.*
import io.ktor.server.config.yaml.*
import io.ktor.server.engine.*
import platform.posix.exit

fun main() {
    embeddedServer(CIO, environment = applicationEngineEnvironment {
//        val conf = YamlConfigLoader().load("./application.yaml")
//            ?: throw RuntimeException("Cannot read application.yaml")
//        println(conf)
//        config = conf
//        println("File read")

        module {
            module()
        }

        connector {
            port =  8080
            host =  "0.0.0.0"
        }
//        println("Starting")
    }).apply {
        addShutdownHook {
            println("Stop is requested")
            stop(3000, 5000)
            println("Exiting")
            exit(0)
        }
        start(true)
    }
}
