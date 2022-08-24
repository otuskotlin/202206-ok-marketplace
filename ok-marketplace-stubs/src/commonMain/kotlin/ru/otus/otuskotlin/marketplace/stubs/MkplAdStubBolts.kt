package ru.otus.otuskotlin.marketplace.stubs

import ru.otus.otuskotlin.marketplace.common.models.*

object MkplAdStubBolts {
    val AD_DEMAND_BOLT1: MkplAd
        get() = MkplAd(
            id = MkplAdId("666"),
            title = "Требуется болт",
            description = "Требуется болт 100x5 с шистигранной шляпкой",
            ownerId = MkplUserId("user-1"),
            adType = MkplDealSide.DEMAND,
            visibility = MkplVisibility.VISIBLE_PUBLIC,
            permissionsClient = mutableSetOf(
                MkplAdPermissionClient.READ,
                MkplAdPermissionClient.UPDATE,
                MkplAdPermissionClient.DELETE,
                MkplAdPermissionClient.MAKE_VISIBLE_PUBLIC,
                MkplAdPermissionClient.MAKE_VISIBLE_GROUP,
                MkplAdPermissionClient.MAKE_VISIBLE_OWNER,
            )
        )
    val AD_SUPPLY_BOLT1 = AD_DEMAND_BOLT1.copy(adType = MkplDealSide.SUPPLY)
}
