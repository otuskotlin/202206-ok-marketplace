package ru.otus.otuskotlin.marketplace.backend.repository.gremlin.mappers

import org.apache.tinkerpop.gremlin.structure.T
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.AdGremlinConst.FIELD_AD_TYPE
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.AdGremlinConst.FIELD_DESCRIPTION
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.AdGremlinConst.FIELD_LOCK
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.AdGremlinConst.FIELD_OWNER_ID
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.AdGremlinConst.FIELD_PRODUCT_ID
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.AdGremlinConst.FIELD_TITLE
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.AdGremlinConst.FIELD_VISIBILITY
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.exceptions.WrongEnumException
import ru.otus.otuskotlin.marketplace.common.models.*

fun Map<Any?, Any?>.toMkplAd(): MkplAd = MkplAd(
    id = (this[T.id] as? String)?.let { MkplAdId(it) } ?: MkplAdId.NONE,
    ownerId = (this[FIELD_OWNER_ID] as? String)?.let { MkplUserId(it) } ?: MkplUserId.NONE,
    lock = (this[FIELD_LOCK] as? String)?.let { MkplAdLock(it) } ?: MkplAdLock.NONE,
    title = (this[FIELD_TITLE] as? String) ?: "",
    description = (this[FIELD_DESCRIPTION] as? String) ?: "",
    adType = when (val value = this[FIELD_AD_TYPE] as? String) {
        MkplDealSide.SUPPLY.name -> MkplDealSide.SUPPLY
        MkplDealSide.DEMAND.name -> MkplDealSide.DEMAND
        null -> MkplDealSide.NONE
        else -> throw WrongEnumException(
            "Cannot convert object from DB. " +
                    "adType = '$value' cannot be converted to ${MkplDealSide::class}"
        )
    },
    visibility = when (val value = this[FIELD_VISIBILITY]) {
        MkplVisibility.VISIBLE_PUBLIC.name -> MkplVisibility.VISIBLE_PUBLIC
        MkplVisibility.VISIBLE_TO_GROUP.name -> MkplVisibility.VISIBLE_TO_GROUP
        MkplVisibility.VISIBLE_TO_OWNER.name -> MkplVisibility.VISIBLE_TO_OWNER
        null -> MkplVisibility.NONE
        else -> throw WrongEnumException(
            "Cannot convert object from DB. " +
                    "visibility = '$value' cannot be converted to ${MkplVisibility::class}"
        )
    },
    productId = (this[FIELD_PRODUCT_ID] as? String)?.let { MkplProductId(it) } ?: MkplProductId.NONE,
)
