package ru.otus.otuskotlin.marketplace.backend.repo.cassandra.model

import ru.otus.otuskotlin.marketplace.common.models.MkplVisibility

enum class AdVisibility {
    VISIBLE_TO_OWNER,
    VISIBLE_TO_GROUP,
    VISIBLE_PUBLIC,
}

fun AdVisibility?.fromTransport() = when(this) {
    null -> MkplVisibility.NONE
    AdVisibility.VISIBLE_TO_OWNER -> MkplVisibility.VISIBLE_TO_OWNER
    AdVisibility.VISIBLE_TO_GROUP -> MkplVisibility.VISIBLE_TO_GROUP
    AdVisibility.VISIBLE_PUBLIC -> MkplVisibility.VISIBLE_PUBLIC
}

fun MkplVisibility.toTransport() = when(this) {
    MkplVisibility.NONE -> null
    MkplVisibility.VISIBLE_TO_OWNER -> AdVisibility.VISIBLE_TO_OWNER
    MkplVisibility.VISIBLE_TO_GROUP -> AdVisibility.VISIBLE_TO_GROUP
    MkplVisibility.VISIBLE_PUBLIC -> AdVisibility.VISIBLE_PUBLIC
}
