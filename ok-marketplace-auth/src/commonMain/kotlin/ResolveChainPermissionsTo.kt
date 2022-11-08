package ru.otus.otuskotlin.marketplace.auth

import ru.otus.otuskotlin.marketplace.common.permissions.MkplUserGroups
import ru.otus.otuskotlin.marketplace.common.permissions.MkplUserPermissions

fun resolveChainPermissions(
    groups: Iterable<MkplUserGroups>,
) = mutableSetOf<MkplUserPermissions>()
    .apply {
        addAll(groups.flatMap { groupPermissionsAdmits[it] ?: emptySet() })
        removeAll(groups.flatMap { groupPermissionsDenys[it] ?: emptySet() }.toSet())
    }
    .toSet()

private val groupPermissionsAdmits = mapOf(
    MkplUserGroups.USER to setOf(
        MkplUserPermissions.READ_OWN,
        MkplUserPermissions.READ_PUBLIC,
        MkplUserPermissions.CREATE_OWN,
        MkplUserPermissions.UPDATE_OWN,
        MkplUserPermissions.DELETE_OWN,
        MkplUserPermissions.OFFER_FOR_OWN,
    ),
    MkplUserGroups.MODERATOR_MP to setOf(),
    MkplUserGroups.ADMIN_AD to setOf(),
    MkplUserGroups.TEST to setOf(),
    MkplUserGroups.BAN_AD to setOf(),
)

private val groupPermissionsDenys = mapOf(
    MkplUserGroups.USER to setOf(),
    MkplUserGroups.MODERATOR_MP to setOf(),
    MkplUserGroups.ADMIN_AD to setOf(),
    MkplUserGroups.TEST to setOf(),
    MkplUserGroups.BAN_AD to setOf(
        MkplUserPermissions.UPDATE_OWN,
        MkplUserPermissions.CREATE_OWN,
    ),
)
