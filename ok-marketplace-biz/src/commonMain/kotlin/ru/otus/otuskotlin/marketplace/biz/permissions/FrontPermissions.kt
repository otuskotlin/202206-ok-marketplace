package ru.otus.otuskotlin.marketplace.biz.permissions

import ru.otus.otuskotlin.marketplace.auth.resolveFrontPermissions
import ru.otus.otuskotlin.marketplace.auth.resolveRelationsTo
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker

fun ICorChainDsl<MkplContext>.frontPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление разрешений пользователей для фронтенда"

    on { state == MkplState.RUNNING }

    handle {
        adRepoDone.permissionsClient.addAll(
            resolveFrontPermissions(
                permissionsChain,
                // Повторно вычисляем отношения, поскольку они могли измениться при выполении операции
                adRepoDone.resolveRelationsTo(principal)
            )
        )

        for (ad in adsRepoDone) {
            ad.permissionsClient.addAll(
                resolveFrontPermissions(
                    permissionsChain,
                    ad.resolveRelationsTo(principal)
                )
            )
        }
    }
}
