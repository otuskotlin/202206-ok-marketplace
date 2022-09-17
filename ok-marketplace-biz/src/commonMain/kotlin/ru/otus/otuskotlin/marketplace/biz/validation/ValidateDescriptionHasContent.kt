package ru.otus.otuskotlin.marketplace.biz.validation

import ru.otus.otuskotlin.marketplace.common.helpers.errorValidation
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.helpers.fail
import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker

fun ICorChainDsl<MkplContext>.validateDescriptionHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { adValidating.description.isNotEmpty() && ! adValidating.description.contains(regExp) }
    handle {
        fail(
            errorValidation(
            field = "description",
            violationCode = "noContent",
            description = "field must contain leters"
        )
        )
    }
}
