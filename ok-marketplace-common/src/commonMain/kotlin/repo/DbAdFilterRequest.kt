package ru.otus.otuskotlin.marketplace.common.repo

import ru.otus.otuskotlin.marketplace.common.models.MkplDealSide
import ru.otus.otuskotlin.marketplace.common.models.MkplUserId

data class DbAdFilterRequest(
    val titleFilter: String = "",
    val ownerId: MkplUserId = MkplUserId.NONE,
    val dealSide: MkplDealSide = MkplDealSide.NONE,
)
