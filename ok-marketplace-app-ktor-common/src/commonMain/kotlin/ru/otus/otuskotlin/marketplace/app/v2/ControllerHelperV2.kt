package ru.otus.otuskotlin.marketplace.app.v2

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.marketplace.api.v2.apiV2Mapper
import ru.otus.otuskotlin.marketplace.api.v2.models.IRequest
import ru.otus.otuskotlin.marketplace.api.v2.models.IResponse
import ru.otus.otuskotlin.marketplace.api.v2.models.ResponseResult
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.helpers.asMkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand
import ru.otus.otuskotlin.marketplace.common.models.MkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.mappers.v2.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v2.toTransportAd

suspend inline fun <reified Q : IRequest, reified R : IResponse>
        ApplicationCall.controllerHelperV2(command: MkplCommand? = null, block: MkplContext.() -> Unit) {
    val ctx = MkplContext(timeStart = Clock.System.now())
    try {
        val request = apiV2Mapper.decodeFromString<Q>(receiveText())
        ctx.fromTransport(request)
        ctx.block()
        val response = apiV2Mapper.encodeToString(ctx.toTransportAd())
        respond(response)
    } catch (e: Throwable) {
        e.printStackTrace()
        command?.also { ctx.command = it }
        ctx.state = MkplState.FAILING
        ctx.errors.add(e.asMkplError())
        ctx.block()
        val response = apiV2Mapper.encodeToString(ctx.toTransportAd())
        respond(response)
    }
}

fun buildError() = MkplError(
    field = "_", code = ResponseResult.ERROR.value
)
