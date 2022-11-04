package ru.otus.otuskotlin.marketplace.biz.repo

import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker

fun ICorChainDsl<MkplContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == MkplState.RUNNING }
    handle {
        adRepoRead = adValidated.deepCopy()
        adRepoRead.ownerId = principal.id
        adRepoPrepare = adRepoRead

    }
}
