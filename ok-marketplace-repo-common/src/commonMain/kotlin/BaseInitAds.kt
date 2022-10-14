package ru.otus.otuskotlin.marketplace.backend.repo.common

import ru.otus.otuskotlin.marketplace.common.models.*

abstract class BaseInitAds(val op: String): IInitObjects<MkplAd> {

    fun createInitTestModel(
        suf: String,
        ownerId: MkplUserId = MkplUserId("owner-123"),
        adType: MkplDealSide = MkplDealSide.DEMAND,
    ) = MkplAd(
        id = MkplAdId("ad-repo-$op-$suf"),
        title = "$suf stub",
        description = "$suf stub description",
        ownerId = ownerId,
        visibility = MkplVisibility.VISIBLE_TO_OWNER,
        adType = adType,
    )
}
