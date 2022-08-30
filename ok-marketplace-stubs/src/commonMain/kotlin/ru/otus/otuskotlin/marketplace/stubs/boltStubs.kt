package ru.otus.otuskotlin.marketplace.stubs

import ru.otus.otuskotlin.marketplace.common.models.*

object Bolt {
    private fun stubReady() = MkplAd(
        id = MkplAdId(id = "666"),
        title = "Болт наружний",
        description = "Лучшего болта вы в мире не найдёте",
        ownerId = MkplUserId(id = "1945"),
        visibility = MkplVisibility.VISIBLE_PUBLIC,
        adType = MkplDealSide.DEMAND,
        permissionsClient = mutableSetOf(MkplAdPermissionClient.READ)
    )

    private fun stubInProgress() = MkplAd(
        id = MkplAdId(id = "12345678"),
        title = "Пока не знаю какой болт",
        description = "Еще не придумал описание",
        ownerId = MkplUserId(id = "1990"),
        visibility = MkplVisibility.VISIBLE_TO_OWNER,
        adType = MkplDealSide.SUPPLY,
        permissionsClient = mutableSetOf(MkplAdPermissionClient.MAKE_VISIBLE_OWNER)
    )

    fun getModel(model: (MkplAd.() -> Unit)? = null) = model?.let {
        stubReady().apply(it)
    } ?: stubReady()

    fun getModels() = listOf(
        stubReady(),
        stubInProgress()
    )

    fun MkplAd.update(updateableAd: MkplAd) = apply {
        title = updateableAd.title
        description = updateableAd.description
        ownerId = updateableAd.ownerId
        visibility = updateableAd.visibility
        permissionsClient.addAll(updateableAd.permissionsClient)
    }
}
