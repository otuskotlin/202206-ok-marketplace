package ru.otus.otuskotlin.marketplace.app.ktor.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor

fun Route.v1Ad(processor: MkplAdProcessor) {
    route("ad") {
        post("create") {
            call.createAd(processor)
        }
        post("read") {
            call.readAd(processor)
        }
        post("update") {
            call.updateAd(processor)
        }
        post("delete") {
            call.deleteAd(processor)
        }
        post("search") {
            call.searchAd(processor)
        }
    }
}

fun Route.v1Offer(processor: MkplAdProcessor) {
    route("ad") {
        post("offers") {
            call.offersAd(processor)
        }
    }
}
