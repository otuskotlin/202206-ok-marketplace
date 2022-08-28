package ru.otus.otuskotlin.marketplace.app.ktor

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.otus.otuskotlin.marketplace.app.ktor.v2.v2Ad
import ru.otus.otuskotlin.marketplace.app.ktor.v2.v2Offer

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    // Generally not needed as it is replaced by a `routing`
    install(Routing)

//    install(DefaultHeaders)

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        allowCredentials = true
        anyHost() // TODO remove
    }

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }

        route("v2") {
            v2Ad()
            v2Offer()
        }
    }
}
