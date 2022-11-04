package ru.otus.otuskotlin.marketplace.common

import ru.otus.otuskotlin.marketplace.common.models.*
import kotlinx.datetime.Instant
import ru.otus.otuskotlin.marketplace.common.permissions.MkplPrincipalModel
import ru.otus.otuskotlin.marketplace.common.permissions.MkplUserPermissions
import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplAdFilter
import ru.otus.otuskotlin.marketplace.common.models.MkplWorkMode
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository
import ru.otus.otuskotlin.marketplace.common.stubs.MkplStubs

data class MkplContext(
    var settings: MkplSettings = MkplSettings(),
    var command: MkplCommand = MkplCommand.NONE,
    var state: MkplState = MkplState.NONE,
    val errors: MutableList<MkplError> = mutableListOf(),

    var workMode: MkplWorkMode = MkplWorkMode.PROD,
    var stubCase: MkplStubs = MkplStubs.NONE,

    var principal: MkplPrincipalModel = MkplPrincipalModel.NONE,
    val permissionsChain: MutableSet<MkplUserPermissions> = mutableSetOf(),
    var permitted: Boolean = false,

    var adRepo: IAdRepository = IAdRepository.NONE,

    var requestId: MkplRequestId = MkplRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var adRequest: MkplAd = MkplAd(),
    var adFilterRequest: MkplAdFilter = MkplAdFilter(),

    var adValidating: MkplAd = MkplAd(),
    var adFilterValidating: MkplAdFilter = MkplAdFilter(),

    var adValidated: MkplAd = MkplAd(),
    var adFilterValidated: MkplAdFilter = MkplAdFilter(),

    var adRepoRead: MkplAd = MkplAd(),
    var adRepoPrepare: MkplAd = MkplAd(),
    var adRepoDone: MkplAd = MkplAd(),
    var adsRepoDone: MutableList<MkplAd> = mutableListOf(),

    var adResponse: MkplAd = MkplAd(),
    var adsResponse: MutableList<MkplAd> = mutableListOf(),
)
