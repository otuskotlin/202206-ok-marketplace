package ru.otus.otuskotlin.marketplace.serverlessapp.api

import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Request
import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Response
import ru.otus.otuskotlin.marketplace.serverlessapp.api.v1.*
import yandex.cloud.sdk.functions.Context
import yandex.cloud.sdk.functions.YcFunction

@Suppress("unused")
class RoutingHandler : YcFunction<Request, Response> {

    override fun handle(event: Request, context: Context): Response {
        if (event.httpMethod != "POST") {
            return Response(400, emptyMap(), "Invalid http method: ${event.httpMethod}")
        }
        return when {
            event.path.startsWith("v1") -> v1Handles(event, context)
            event.path.startsWith("v2") -> v2Handles(event, context)
            else -> Response(400, emptyMap(), "Unknown api version! Path: ${event.path}")
        }
    }

    private fun v1Handles(event: Request, context: Context): Response =
        when (event.path.substring("v1/".length)) {
            "ad/create" -> CreateAdHandler().handle(event, context)
            "ad/read"   -> ReadAdHandler().handle(event, context)
            "ad/update" -> UpdateAdHandler().handle(event, context)
            "ad/delete" -> DeleteAdHandler().handle(event, context)
            "ad/search" -> SearchAdHandler().handle(event, context)
            "ad/offers" -> OffersAdHandler().handle(event, context)
            else -> Response(400, emptyMap(), "Unknown path! Path: ${event.path}")
        }

    private fun v2Handles(event: Request, context: Context): Response =
        when (event.path.substring("v2/".length)) {
            "ad/create" -> ru.otus.otuskotlin.marketplace.serverlessapp.api.v2.CreateAdHandler().handle(event, context)
            "ad/read"   -> ru.otus.otuskotlin.marketplace.serverlessapp.api.v2.ReadAdHandler().handle(event, context)
            "ad/update" -> ru.otus.otuskotlin.marketplace.serverlessapp.api.v2.UpdateAdHandler().handle(event, context)
            "ad/delete" -> ru.otus.otuskotlin.marketplace.serverlessapp.api.v2.DeleteAdHandler().handle(event, context)
            "ad/search" -> ru.otus.otuskotlin.marketplace.serverlessapp.api.v2.SearchAdHandler().handle(event, context)
            "ad/offers" -> ru.otus.otuskotlin.marketplace.serverlessapp.api.v2.OffersAdHandler().handle(event, context)
            else -> Response(400, emptyMap(), "Unknown path! Path: ${event.path}")
        }
}
