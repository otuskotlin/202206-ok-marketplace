package ru.otus.otuskotlin.marketplace.serverlessapp.api

import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Request
import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Response
import ru.otus.otuskotlin.marketplace.serverlessapp.api.v1.v1Handlers
import ru.otus.otuskotlin.marketplace.serverlessapp.api.v2.v2Handlers
import yandex.cloud.sdk.functions.Context
import yandex.cloud.sdk.functions.YcFunction

@Suppress("unused")
class RoutingHandler : YcFunction<Request, Response> {

    override fun handle(event: Request, context: Context): Response {
        if (event.httpMethod != "POST") {
            return Response(400, emptyMap(), "Invalid http method: ${event.httpMethod}")
        }
        return when {
            event.path.startsWith("v1") -> v1Handlers(event, context)
            event.path.startsWith("v2") -> v2Handlers(event, context)
            else -> Response(400, emptyMap(), "Unknown api version! Path: ${event.path}")
        }
    }
}
