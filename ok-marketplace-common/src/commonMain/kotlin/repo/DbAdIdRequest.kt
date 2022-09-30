package ru.otus.otuskotlin.marketplace.common.repo

import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplAdId
import ru.otus.otuskotlin.marketplace.common.models.MkplAdLock

data class DbAdIdRequest(
    val id: MkplAdId,
    val lock: MkplAdLock = MkplAdLock.NONE,
) {
    constructor(ad: MkplAd): this(ad.id, ad.lock)
}
