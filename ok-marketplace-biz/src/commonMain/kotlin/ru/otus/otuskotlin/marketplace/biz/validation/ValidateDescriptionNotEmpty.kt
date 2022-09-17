package ru.otus.otuskotlin.marketplace.biz.validation

import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker
import ru.otus.otuskotlin.marketplace.common.helpers.errorValidation
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.helpers.fail

fun ICorChainDsl<MkplContext>.validateDescriptionNotEmpty(title: String) = worker {
    this.title = title
    on { adValidating.description.isEmpty() }
    handle {
        fail(
            errorValidation(
            field = "description",
            violationCode = "empty",
            description = "field must not be empty"
        )
        )
    }
}
