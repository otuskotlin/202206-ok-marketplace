package ru.otus.otuskotlin.markeplace.springapp.api

import ru.otus.otuskotlin.marketplace.common.MkplContext

fun <REQ, RESP> controllerAction(request: REQ,
                                 fromTransport: MkplContext.(REQ) -> Unit,
                                 processor: (MkplContext) -> MkplContext,
                                 toTransport: MkplContext.() -> RESP): RESP =
    MkplContext()
        .apply { fromTransport(request) }
        .let { processor(it) }
        .toTransport()
