package ru.otus.otuskotlin.marketplace.biz.validation

import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker

fun ICorChainDsl<MkplContext>.finishAdValidation(title: String) = worker {
    this.title = title
    on { state == MkplState.RUNNING }
    handle {
        adValidated = adValidating
    }
}

fun ICorChainDsl<MkplContext>.finishAdFilterValidation(title: String) = worker {
    this.title = title
    on { state == MkplState.RUNNING }
    handle {
        adFilterValidated = adFilterValidating
    }
}
