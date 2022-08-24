package ru.otus.otuskotlin.marketplace.common.helpers

import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplState

fun Throwable.asMkplError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = MkplError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun MkplContext.addError(error: MkplError) = errors.add(error)
fun MkplContext.fail(error: MkplError) {
    addError(error)
    state = MkplState.FAILING
}
