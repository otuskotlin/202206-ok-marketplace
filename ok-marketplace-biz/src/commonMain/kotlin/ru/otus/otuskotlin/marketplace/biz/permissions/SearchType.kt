package ru.otus.otuskotlin.marketplace.biz.permissions

import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.common.models.MkplSearchPermissions
import ru.otus.otuskotlin.marketplace.common.permissions.MkplUserPermissions
import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.chain
import ru.otus.otuskotlin.marketplace.cor.worker

fun ICorChainDsl<MkplContext>.searchTypes(title: String) = chain {
    this.title = title
    description = "Добавление ограничений в поисковый запрос согласно правам доступа и др. политикам"
    on { state == MkplState.RUNNING }
    worker("Определение типа поиска") {
        adFilterValidated.searchPermissions = setOfNotNull(
            MkplSearchPermissions.OWN.takeIf { permissionsChain.contains(MkplUserPermissions.SEARCH_OWN) },
            MkplSearchPermissions.PUBLIC.takeIf { permissionsChain.contains(MkplUserPermissions.SEARCH_PUBLIC) },
            MkplSearchPermissions.REGISTERED.takeIf { permissionsChain.contains(MkplUserPermissions.SEARCH_REGISTERED) },
        ).toMutableSet()
    }
}
