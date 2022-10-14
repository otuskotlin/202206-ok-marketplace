package ru.otus.otuskotlin.marketplace.backend.repository.inmemory.model

import ru.otus.otuskotlin.marketplace.common.models.*

data class AdEntity(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val ownerId: String? = null,
    val adType: String? = null,
    val visibility: String? = null,
    val lock: String? = null,
) {
    constructor(model: MkplAd): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        title = model.title.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        adType = model.adType.takeIf { it != MkplDealSide.NONE }?.name,
        visibility = model.visibility.takeIf { it != MkplVisibility.NONE }?.name,
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = MkplAd(
        id = id?.let { MkplAdId(it) }?: MkplAdId.NONE,
        title = title?: "",
        description = description?: "",
        ownerId = ownerId?.let { MkplUserId(it) }?: MkplUserId.NONE,
        adType = adType?.let { MkplDealSide.valueOf(it) }?: MkplDealSide.NONE,
        visibility = visibility?.let { MkplVisibility.valueOf(it) }?: MkplVisibility.NONE,
        lock = lock?.let { MkplAdLock(it) } ?: MkplAdLock.NONE,
    )
}
