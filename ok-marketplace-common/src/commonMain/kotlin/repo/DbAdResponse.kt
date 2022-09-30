package ru.otus.otuskotlin.marketplace.common.repo

import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplError

data class DbAdResponse(
    override val data: MkplAd?,
    override val isSuccess: Boolean,
    override val errors: List<MkplError> = emptyList()
): IDbResponse<MkplAd>
