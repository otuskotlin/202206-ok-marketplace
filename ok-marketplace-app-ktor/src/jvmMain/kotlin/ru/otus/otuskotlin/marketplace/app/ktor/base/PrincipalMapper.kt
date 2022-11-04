package ru.otus.otuskotlin.marketplace.app.ktor.base

import io.ktor.server.auth.jwt.*
import ru.otus.otuskotlin.marketplace.app.ktor.base.KtorAuthConfig.Companion.F_NAME_CLAIM
import ru.otus.otuskotlin.marketplace.app.ktor.base.KtorAuthConfig.Companion.GROUPS_CLAIM
import ru.otus.otuskotlin.marketplace.app.ktor.base.KtorAuthConfig.Companion.ID_CLAIM
import ru.otus.otuskotlin.marketplace.app.ktor.base.KtorAuthConfig.Companion.L_NAME_CLAIM
import ru.otus.otuskotlin.marketplace.app.ktor.base.KtorAuthConfig.Companion.M_NAME_CLAIM
import ru.otus.otuskotlin.marketplace.common.permissions.MkplPrincipalModel
import ru.otus.otuskotlin.marketplace.common.permissions.MkplUserGroups
import ru.otus.otuskotlin.marketplace.common.models.MkplUserId

fun JWTPrincipal?.toModel() = this?.run {
    MkplPrincipalModel(
        id = payload.getClaim(ID_CLAIM).asString()?.let { MkplUserId(it) } ?: MkplUserId.NONE,
        fname = payload.getClaim(F_NAME_CLAIM).asString() ?: "",
        mname = payload.getClaim(M_NAME_CLAIM).asString() ?: "",
        lname = payload.getClaim(L_NAME_CLAIM).asString() ?: "",
        groups = payload
            .getClaim(GROUPS_CLAIM)
            ?.asList(String::class.java)
            ?.mapNotNull {
                try {
                    MkplUserGroups.valueOf(it)
                } catch (e: Throwable) {
                    null
                }
            }?.toSet() ?: emptySet()
    )
} ?: MkplPrincipalModel.NONE
