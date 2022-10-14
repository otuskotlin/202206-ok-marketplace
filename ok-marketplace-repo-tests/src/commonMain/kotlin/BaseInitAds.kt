package ru.otus.otuskotlin.marketplace.backend.repo.tests

import ru.otus.otuskotlin.marketplace.common.models.*

abstract class BaseInitAds(val op: String): IInitObjects<MkplAd> {

    open val lockOld: MkplAdLock = MkplAdLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: MkplAdLock = MkplAdLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        ownerId: MkplUserId = MkplUserId("owner-123"),
        adType: MkplDealSide = MkplDealSide.DEMAND,
        lock: MkplAdLock = lockOld,
    ) = MkplAd(
        id = MkplAdId("ad-repo-$op-$suf"),
        title = "$suf stub",
        description = "$suf stub description",
        ownerId = ownerId,
        visibility = MkplVisibility.VISIBLE_TO_OWNER,
        adType = adType,
        lock = lock,
    )
}
