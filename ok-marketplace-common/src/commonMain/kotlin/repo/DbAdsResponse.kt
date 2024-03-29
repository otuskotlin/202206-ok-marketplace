package ru.otus.otuskotlin.marketplace.common.repo

import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplError

data class DbAdsResponse(
    override val data: List<MkplAd>?,
    override val isSuccess: Boolean,
    override val errors: List<MkplError> = emptyList(),
): IDbResponse<List<MkplAd>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbAdsResponse(emptyList(), true)
        val MOCK_SUCCESS_NONE get() =  DbAdsResponse(listOf(MkplAd.NONE), true)

        fun success(result: List<MkplAd>) = DbAdsResponse(result, true)
        fun error(errors: List<MkplError>) = DbAdsResponse(null, false, errors)
        fun error(error: MkplError) = DbAdsResponse(null, false, listOf(error))
    }
}
