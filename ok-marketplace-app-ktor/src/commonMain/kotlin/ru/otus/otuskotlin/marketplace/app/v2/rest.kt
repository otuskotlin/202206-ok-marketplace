package ru.otus.otuskotlin.marketplace.app.v2

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.v2Ad() {
    route("ad") {
        post("create") {
            call.createAd()
        }
        post("read") {
            call.readAd()
        }
        post("update") {
            call.updateAd()
        }
        post("delete") {
            call.deleteAd()
        }
        post("search") {
            call.searchAd()
        }
    }
}

fun Route.v2Offer() {
    route("ad") {
        post("offers") {
            call.offersAd()
        }
    }
}
