package ru.otus.otuskotlin.marketplace.app.ktor

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import ru.otus.otuskotlin.marketplace.app.ktor.base.KtorWsSessions
import ru.otus.otuskotlin.marketplace.app.ktor.v2.mpWsHandlerV2
import ru.otus.otuskotlin.marketplace.app.ktor.v2.v2Ad
import ru.otus.otuskotlin.marketplace.app.ktor.v2.v2Offer
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.models.MkplSettings
import ru.otus.otuskotlin.marketplace.repo.inmemory.AdRepoInMemory

@Suppress("unused") // Referenced in application.conf
fun Application.module(settings: MkplSettings? = null,) {
    // Generally not needed as it is replaced by a `routing`
    install(Routing)
    install(WebSockets)

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
    val corSettings by lazy {
        settings ?: MkplSettings(
            repoTest = AdRepoInMemory()
        )
    }
    val processor = MkplAdProcessor(settings = corSettings)

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }

        route("v2") {
            v2Ad(processor)
            v2Offer(processor)
        }
        webSocket("/ws/v2") {
            mpWsHandlerV2(
                processor = processor,
                sessions = KtorWsSessions.sessions,
            )
        }
    }
}
