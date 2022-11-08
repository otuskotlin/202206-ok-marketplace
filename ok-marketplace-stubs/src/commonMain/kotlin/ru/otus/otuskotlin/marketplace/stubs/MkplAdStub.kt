package ru.otus.otuskotlin.marketplace.stubs

import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.permissions.MkplAdPermissionClient

object MkplAdStub {
    fun get() = MkplAd(
        id = MkplAdId("666"),
        title = "Требуется болт",
        description = "Требуется болт 100x5 с шестигранной шляпкой",
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

    fun prepareResult(block: MkplAd.() -> Unit): MkplAd = get().apply(block)

    fun prepareSearchList(filter: String, type: MkplDealSide) = listOf(
        mkplAdDemand("d-666-01", filter, type),
        mkplAdDemand("d-666-02", filter, type),
        mkplAdDemand("d-666-03", filter, type),
        mkplAdDemand("d-666-04", filter, type),
        mkplAdDemand("d-666-05", filter, type),
        mkplAdDemand("d-666-06", filter, type),
    )

    fun prepareOffersList(filter: String, type: MkplDealSide) = listOf(
        mkplAdSupply("s-666-01", filter, type),
        mkplAdSupply("s-666-02", filter, type),
        mkplAdSupply("s-666-03", filter, type),
        mkplAdSupply("s-666-04", filter, type),
        mkplAdSupply("s-666-05", filter, type),
        mkplAdSupply("s-666-06", filter, type),
    )

    private fun mkplAdDemand(id: String, filter: String, type: MkplDealSide) =
        mkplAd(get(), id = id, filter = filter, type = type)

    private fun mkplAdSupply(id: String, filter: String, type: MkplDealSide) =
        mkplAd(get(), id = id, filter = filter, type = type)

    private fun mkplAd(base: MkplAd, id: String, filter: String, type: MkplDealSide) = base.copy(
        id = MkplAdId(id),
        title = "$filter $id",
        description = "desc $filter $id",
        adType = type,
    )

}
