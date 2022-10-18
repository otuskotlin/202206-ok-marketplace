package ru.otus.otuskotlin.marketplace.backend.repository.gremlin

object AdGremlinConst {
    const val RESULT_SUCCESS = "success"
    const val RESULT_LOCK_FAILURE = "lock-failure"

    const val FIELD_ID = "#id"
    const val FIELD_TITLE = "title"
    const val FIELD_DESCRIPTION = "description"
    const val FIELD_AD_TYPE = "adType"
    const val FIELD_OWNER_ID = "ownerId"
    const val FIELD_VISIBILITY = "visibility"
    const val FIELD_PRODUCT_ID = "productId"
    const val FIELD_LOCK = "lock"
    const val FIELD_TMP_RESULT = "_result"
}
