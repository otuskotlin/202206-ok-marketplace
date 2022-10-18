package ru.otus.otuskotlin.marketplace.backend.repository.gremlin.mappers

import ru.otus.otuskotlin.marketplace.common.models.MkplAd

fun MkplAd.label(): String? = this::class.simpleName
