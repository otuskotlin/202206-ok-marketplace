package ru.otus.otuskotlin.marketplace

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

// function with config (application.conf)
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
    }
}
