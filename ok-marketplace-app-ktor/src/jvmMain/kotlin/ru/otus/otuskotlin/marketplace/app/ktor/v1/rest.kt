package ru.otus.otuskotlin.marketplace.app.ktor.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.otus.otuskotlin.marketplace.app.ktor.v2.v2Ad
import ru.otus.otuskotlin.marketplace.app.ktor.v2.v2Offer
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.logging.common.mpLogger

private val loggerAd = mpLogger(Route::v2Ad::class)
private val loggerOffers = mpLogger(Route::v2Offer::class)

fun Route.v1Ad(processor: MkplAdProcessor) {
    route("ad") {
        post("create") {
            call.createAd(processor, loggerAd)
        }
        post("read") {
            call.readAd(processor, loggerAd)
        }
        post("update") {
            call.updateAd(processor, loggerAd)
        }
        post("delete") {
            call.deleteAd(processor, loggerAd)
        }
        post("search") {
            call.searchAd(processor, loggerAd)
        }
    }
}

fun Route.v1Offer(processor: MkplAdProcessor) {
    route("ad") {
        post("offers") {
            call.offersAd(processor, loggerOffers)
        }
    }
}
