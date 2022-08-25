package ru.otus.otuskotlin.marketplace.serverlessapp.api.v2

import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Request
import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Response
import yandex.cloud.sdk.functions.Context

fun v2Handlers(event: Request, context: Context): Response =
    when (event.path.substring("v2/".length)) {
        "ad/create" -> createAdHandler.handle(event, context)
        "ad/read"   -> readAdHandler.handle(event, context)
        "ad/update" -> updateAdHandler.handle(event, context)
        "ad/delete" -> deleteAdHandler.handle(event, context)
        "ad/search" -> searchAdHandler.handle(event, context)
        "ad/offers" -> offersAdHandler.handle(event, context)
        else -> Response(400, emptyMap(), "Unknown path! Path: ${event.path}")
    }