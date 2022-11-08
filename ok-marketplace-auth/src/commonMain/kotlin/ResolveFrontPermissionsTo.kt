package ru.otus.otuskotlin.marketplace.auth

import ru.otus.otuskotlin.marketplace.common.permissions.MkplAdPermissionClient
import ru.otus.otuskotlin.marketplace.common.permissions.MkplPrincipalRelations
import ru.otus.otuskotlin.marketplace.common.permissions.MkplUserPermissions

fun resolveFrontPermissions(
    permissions: Iterable<MkplUserPermissions>,
    relations: Iterable<MkplPrincipalRelations>,
) = mutableSetOf<MkplAdPermissionClient>()
    .apply {
        for (permission in permissions) {
            for (relation in relations) {
                accessTable[permission]?.get(relation)?.let { this@apply.add(it) }
            }
        }
    }
    .toSet()

private val accessTable = mapOf(
    // READ
    MkplUserPermissions.READ_OWN to mapOf(
        MkplPrincipalRelations.OWN to MkplAdPermissionClient.READ
    ),
    MkplUserPermissions.READ_GROUP to mapOf(
        MkplPrincipalRelations.GROUP to MkplAdPermissionClient.READ
    ),
    MkplUserPermissions.READ_PUBLIC to mapOf(
        MkplPrincipalRelations.PUBLIC to MkplAdPermissionClient.READ
    ),
    MkplUserPermissions.READ_CANDIDATE to mapOf(
        MkplPrincipalRelations.MODERATABLE to MkplAdPermissionClient.READ
    ),

    // UPDATE
    MkplUserPermissions.UPDATE_OWN to mapOf(
        MkplPrincipalRelations.OWN to MkplAdPermissionClient.UPDATE
    ),
    MkplUserPermissions.UPDATE_PUBLIC to mapOf(
        MkplPrincipalRelations.MODERATABLE to MkplAdPermissionClient.UPDATE
    ),
    MkplUserPermissions.UPDATE_CANDIDATE to mapOf(
        MkplPrincipalRelations.MODERATABLE to MkplAdPermissionClient.UPDATE
    ),

    // DELETE
    MkplUserPermissions.DELETE_OWN to mapOf(
        MkplPrincipalRelations.OWN to MkplAdPermissionClient.DELETE
    ),
    MkplUserPermissions.DELETE_PUBLIC to mapOf(
        MkplPrincipalRelations.MODERATABLE to MkplAdPermissionClient.DELETE
    ),
    MkplUserPermissions.DELETE_CANDIDATE to mapOf(
        MkplPrincipalRelations.MODERATABLE to MkplAdPermissionClient.DELETE
    ),
)
