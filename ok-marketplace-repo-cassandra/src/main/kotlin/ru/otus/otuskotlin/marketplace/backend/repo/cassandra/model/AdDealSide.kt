package ru.otus.otuskotlin.marketplace.backend.repo.cassandra.model

import ru.otus.otuskotlin.marketplace.common.models.MkplDealSide

enum class AdDealSide {
    DEMAND,
    SUPPLY,
}

fun AdDealSide?.fromTransport() = when(this) {
    null -> MkplDealSide.NONE
    AdDealSide.DEMAND -> MkplDealSide.DEMAND
    AdDealSide.SUPPLY -> MkplDealSide.SUPPLY
}

fun MkplDealSide.toTransport() = when(this) {
    MkplDealSide.NONE -> null
    MkplDealSide.DEMAND -> AdDealSide.DEMAND
    MkplDealSide.SUPPLY -> AdDealSide.SUPPLY
}

