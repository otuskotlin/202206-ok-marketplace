package ru.otus.otuskotlin.marketplace.backend.services

import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplState

fun MkplContext.errorResponse(buildError: () -> MkplError, error: (MkplError) -> MkplError) = apply {
    state = MkplState.FAILING
    errors.add(error(buildError()))
}

fun MkplContext.successResponse(context: MkplContext.() -> Unit) = apply(context)
    .apply { state = MkplState.RUNNING }

val notFoundError: (String) -> String = { "Not found ad by id $it" }
