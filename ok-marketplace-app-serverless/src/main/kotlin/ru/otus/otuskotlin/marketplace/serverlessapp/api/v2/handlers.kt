package ru.otus.otuskotlin.marketplace.serverlessapp.api.v2

import kotlinx.datetime.Clock
import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Request
import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Response
import ru.otus.otuskotlin.marketplace.serverlessapp.api.utils.toResponse
import ru.otus.otuskotlin.marketplace.serverlessapp.api.utils.toTransportModel
import ru.otus.otuskotlin.marketplace.api.v2.models.*
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplRequestId
import ru.otus.otuskotlin.marketplace.mappers.v2.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v2.toTransportCreate
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub
import yandex.cloud.sdk.functions.Context
import yandex.cloud.sdk.functions.YcFunction


val createAdHandler = YcFunction<Request, Response> { input, context ->
    println("CreateAdHandler v2 start work")
    val request = input.toTransportModel<AdCreateRequest>()
    val mkplContext = MkplContext(
        timeStart = Clock.System.now(),
        requestId = MkplRequestId(context.requestId)
    )
    mkplContext.fromTransport(request)
    mkplContext.adResponse = MkplAdStub.get()
    mkplContext.toTransportCreate().toResponse()
}

val readAdHandler = YcFunction<Request, Response> { input, context ->
    println("ReadAdHandler v2 start work")
    val request = input.toTransportModel<AdReadRequest>()
    val mkplContext = MkplContext(
        timeStart = Clock.System.now(),
        requestId = MkplRequestId(context.requestId)
    )
    mkplContext.fromTransport(request)
    mkplContext.adResponse = MkplAdStub.get()
    mkplContext.toTransportCreate().toResponse()
}

val updateAdHandler = YcFunction<Request, Response> { input, context ->
    println("UpdateAdHandler v2 start work")
    val request = input.toTransportModel<AdUpdateRequest>()
    val mkplContext = MkplContext(
        timeStart = Clock.System.now(),
        requestId = MkplRequestId(context.requestId)
    )
    mkplContext.fromTransport(request)
    mkplContext.adResponse = MkplAdStub.get()
    mkplContext.toTransportCreate().toResponse()
}

val deleteAdHandler = YcFunction<Request, Response> { input, context ->
    println("DeleteAdHandler v2 start work")
    val request = input.toTransportModel<AdDeleteRequest>()
    val mkplContext = MkplContext(
        timeStart = Clock.System.now(),
        requestId = MkplRequestId(context.requestId)
    )
    mkplContext.fromTransport(request)
    mkplContext.toTransportCreate().toResponse()
}

val searchAdHandler = YcFunction<Request, Response> { input, context ->
    println("SearchAdHandler v2 start work")
    val request = input.toTransportModel<AdSearchRequest>()
    val mkplContext = MkplContext(
        timeStart = Clock.System.now(),
        requestId = MkplRequestId(context.requestId)
    )
    mkplContext.fromTransport(request)
    mkplContext.adResponse = MkplAdStub.get()
    mkplContext.toTransportCreate().toResponse()
}

val offersAdHandler = YcFunction<Request, Response> { input, context ->
    println("OffersAdHandler v2 start work")
    val request = input.toTransportModel<AdOffersRequest>()
    val mkplContext = MkplContext(
        timeStart = Clock.System.now(),
        requestId = MkplRequestId(context.requestId)
    )
    mkplContext.fromTransport(request)
    mkplContext.adsResponse.add(MkplAdStub.get())
    mkplContext.toTransportCreate().toResponse()
}