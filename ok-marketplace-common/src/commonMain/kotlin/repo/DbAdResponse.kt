package ru.otus.otuskotlin.marketplace.common.repo

import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplError

data class DbAdResponse(
    override val data: MkplAd?,
    override val isSuccess: Boolean,
    override val errors: List<MkplError> = emptyList()
): IDbResponse<MkplAd> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbAdResponse(null, true)
        val MOCK_SUCCESS_NONE get() = DbAdResponse(MkplAd.NONE, true)

        fun success(result: MkplAd) = DbAdResponse(result, true)
        fun error(errors: List<MkplError>) = DbAdResponse(null, false, errors)
        fun error(error: MkplError) = DbAdResponse(null, false, listOf(error))
    }
}
