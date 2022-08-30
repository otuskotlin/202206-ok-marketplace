package ru.otus.otuskotlin.marketplace.stubs

import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplError

data class MkplAdStubResponse(
    val ad: MkplAd,
    val errors: List<MkplError> = emptyList(),
)
