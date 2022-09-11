package ru.otus.otuskotlin.marketplace.biz.validation

import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.chain

fun ICorChainDsl<MkplContext>.validation(block: ICorChainDsl<MkplContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == MkplState.RUNNING }
}
