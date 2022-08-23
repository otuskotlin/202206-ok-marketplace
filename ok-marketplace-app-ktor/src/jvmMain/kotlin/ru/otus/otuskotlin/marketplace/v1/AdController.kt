package ru.otus.otuskotlin.marketplace.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplDealSide
import ru.otus.otuskotlin.marketplace.mappers.v1.*
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub

suspend fun ApplicationCall.createAd() {
    val request = receive<AdCreateRequest>()
    val context = MkplContext()
    context.fromTransport(request)
    context.adResponse = MkplAdStub.get()
    respond(context.toTransportCreate())
}

suspend fun ApplicationCall.readAd() {
    val request = receive<AdReadRequest>()
    val context = MkplContext()
    context.fromTransport(request)
    context.adResponse = MkplAdStub.get()
    respond(context.toTransportRead())
}

suspend fun ApplicationCall.updateAd() {
    val request = receive<AdUpdateRequest>()
    val context = MkplContext()
    context.fromTransport(request)
    context.adResponse = MkplAdStub.get()
    respond(context.toTransportUpdate())
}

suspend fun ApplicationCall.deleteAd() {
    val request = receive<AdDeleteRequest>()
    val context = MkplContext()
    context.fromTransport(request)
    context.adResponse = MkplAdStub.get()
    respond(context.toTransportDelete())
}

suspend fun ApplicationCall.searchAd() {
    val request = receive<AdSearchRequest>()
    val context = MkplContext()
    context.fromTransport(request)
    context.adsResponse.addAll(MkplAdStub.prepareSearchList("Болт", MkplDealSide.DEMAND))
    respond(context.toTransportSearch())
}
