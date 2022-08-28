package ru.otus.otuskotlin.marketplace.app.ktor

import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import org.slf4j.event.Level
import ru.otus.otuskotlin.marketplace.api.v1.apiV1Mapper
import ru.otus.otuskotlin.marketplace.app.ktor.v1.v1Ad
import ru.otus.otuskotlin.marketplace.app.ktor.v1.v1Offer

@Suppress("unused") // Referenced in application.conf
fun main() {
    embeddedServer(CIO, environment = applicationEngineEnvironment {
//        val conf = YamlConfigLoader().load("./application.yaml")
//            ?: throw RuntimeException("Cannot read application.yaml")
//        println(conf)
//        config = conf
//        println("File read")

        module {
            module()
            moduleJvm()
        }
    }).apply {
        addShutdownHook {
            println("Stop is requested")
            stop(3000, 5000)
            println("Exiting")
        }
        start(true)
    }
}

fun Application.moduleJvm() {
    install(ContentNegotiation) {
        jackson {
            setConfig(apiV1Mapper.serializationConfig)
            setConfig(apiV1Mapper.deserializationConfig)
        }
    }
    install(CallLogging) {
        level = Level.INFO
    }
    routing {
        route("v1") {
            v1Ad()
            v1Offer()
        }
    }
}
