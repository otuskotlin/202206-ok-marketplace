package ru.otus.otuskotlin.marketplace.app.v2

import ru.otus.otuskotlin.marketplace.api.v2.models.ResponseResult
import ru.otus.otuskotlin.marketplace.common.models.MkplError

fun buildError() = MkplError(
    field = "_", code = ResponseResult.ERROR.value
)


