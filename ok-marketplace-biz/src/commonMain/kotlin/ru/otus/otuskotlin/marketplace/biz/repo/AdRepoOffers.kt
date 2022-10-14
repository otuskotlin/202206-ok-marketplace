package ru.otus.otuskotlin.marketplace.biz.repo

import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplDealSide
import ru.otus.otuskotlin.marketplace.common.models.MkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.common.repo.DbAdFilterRequest
import ru.otus.otuskotlin.marketplace.common.repo.DbAdsResponse
import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker

fun ICorChainDsl<MkplContext>.repoOffers(title: String) = worker {
    this.title = title
    description = "Поиск предложений для объявления по названию"
    on { state == MkplState.RUNNING }
    handle {
        val adRequest = adRepoPrepare
        val filter = DbAdFilterRequest(
            titleFilter = adRequest.title,
            dealSide = when (adRequest.adType) {
                MkplDealSide.DEMAND -> MkplDealSide.SUPPLY
                MkplDealSide.SUPPLY -> MkplDealSide.DEMAND
                MkplDealSide.NONE -> MkplDealSide.NONE
            }
        )
        val dbResponse = if (filter.dealSide == MkplDealSide.NONE) {
            DbAdsResponse(
                data = null,
                isSuccess = false,
                errors = listOf(
                    MkplError(
                        field = "adType",
                        message = "Type of ad must not be empty"
                    )
                )
            )
        } else {
            adRepo.searchAd(filter)
        }

        val resultAds = dbResponse.data
        when {
            !resultAds.isNullOrEmpty() -> adsRepoDone = resultAds.toMutableList()
            dbResponse.isSuccess -> return@handle
            else -> {
                state = MkplState.FAILING
                errors.addAll(dbResponse.errors)
            }
        }
    }
}
