package ru.otus.otuskotlin.marketplace.common.models

data class MkplAd(
    var id: MkplAdId = MkplAdId.NONE,
    var title: String = "",
    var description: String = "",
    var ownerId: MkplUserId = MkplUserId.NONE,
    var adType: MkplDealSide = MkplDealSide.NONE,
    var visibility: MkplVisibility = MkplVisibility.NONE,
    var productId: MkplProductId = MkplProductId.NONE,
    var lock: MkplAdLock = MkplAdLock.NONE,
    val permissionsClient: MutableSet<MkplAdPermissionClient> = mutableSetOf()
) {
    fun deepCopy(): MkplAd = copy(
        permissionsClient = permissionsClient.toMutableSet()
    )
}
