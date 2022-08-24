package ru.otus.otuskotlin.marketplace.serverlessapp.api.v1

import kotlinx.datetime.Clock
import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Request
import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Response
import ru.otus.otuskotlin.marketplace.serverlessapp.api.utils.toResponse
import ru.otus.otuskotlin.marketplace.serverlessapp.api.utils.toTransportModel
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplRequestId
import ru.otus.otuskotlin.marketplace.mappers.v1.*
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub
import yandex.cloud.sdk.functions.Context
import yandex.cloud.sdk.functions.YcFunction

val createAdHandler = YcFunction<Request, Response> { input, context ->
    println("CreateAdHandler v1 start work")
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
    println("ReadAdHandler v1 start work")
    val request = input.toTransportModel<AdReadRequest>()
    val mkplContext = MkplContext(
        timeStart = Clock.System.now(),
        requestId = MkplRequestId(context.requestId)
    )
    mkplContext.fromTransport(request)
    mkplContext.adResponse = MkplAdStub.get()
    mkplContext.toTransportRead().toResponse()
}

val updateAdHandler = YcFunction<Request, Response> { input, context ->
    println("UpdateAdHandler v1 start work")
    val request = input.toTransportModel<AdUpdateRequest>()
    val mkplContext = MkplContext(
        timeStart = Clock.System.now(),
        requestId = MkplRequestId(context.requestId)
    )
    mkplContext.fromTransport(request)
    mkplContext.adResponse = MkplAdStub.get()
    mkplContext.toTransportUpdate().toResponse()

}

val deleteAdHandler = YcFunction<Request, Response> { input, context ->
    println("DeleteAdHandler v1 start work")
    val request = input.toTransportModel<AdDeleteRequest>()
    val mkplContext = MkplContext(
        timeStart = Clock.System.now(),
        requestId = MkplRequestId(context.requestId)
    )
    mkplContext.fromTransport(request)
    mkplContext.toTransportDelete().toResponse()
}

val searchAdHandler = YcFunction<Request, Response> { input, context ->
    println("SearchAdHandler v1 start work")
    val request = input.toTransportModel<AdSearchRequest>()
    val mkplContext = MkplContext(
        timeStart = Clock.System.now(),
        requestId = MkplRequestId(context.requestId)
    )
    mkplContext.fromTransport(request)
    mkplContext.adResponse = MkplAdStub.get()
    mkplContext.toTransportSearch().toResponse()

}

val offersAdHandler = YcFunction<Request, Response> { input, context ->
    println("OffersAdHandler v1 start work")
    val request = input.toTransportModel<AdOffersRequest>()
    val mkplContext = MkplContext(
        timeStart = Clock.System.now(),
        requestId = MkplRequestId(context.requestId)
    )
    mkplContext.fromTransport(request)
    mkplContext.adsResponse.add(MkplAdStub.get())
    mkplContext.toTransportOffers().toResponse()
}