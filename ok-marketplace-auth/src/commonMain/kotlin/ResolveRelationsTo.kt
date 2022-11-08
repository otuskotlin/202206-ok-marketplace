package ru.otus.otuskotlin.marketplace.auth

import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplAdId
import ru.otus.otuskotlin.marketplace.common.models.MkplVisibility
import ru.otus.otuskotlin.marketplace.common.permissions.MkplPrincipalModel
import ru.otus.otuskotlin.marketplace.common.permissions.MkplPrincipalRelations

fun MkplAd.resolveRelationsTo(principal: MkplPrincipalModel): Set<MkplPrincipalRelations> = setOfNotNull(
    MkplPrincipalRelations.NONE,
    MkplPrincipalRelations.NEW.takeIf { id == MkplAdId.NONE },
    MkplPrincipalRelations.OWN.takeIf { principal.id == ownerId },
    MkplPrincipalRelations.MODERATABLE.takeIf { visibility != MkplVisibility.VISIBLE_TO_OWNER },
    MkplPrincipalRelations.PUBLIC.takeIf { visibility == MkplVisibility.VISIBLE_PUBLIC },
)
