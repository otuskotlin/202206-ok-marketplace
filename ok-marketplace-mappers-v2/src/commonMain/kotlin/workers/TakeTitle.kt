package ru.otus.otuskotlin.marketplace.mappers.v2.workers

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.otuskotlin.marketplace.api.v2.models.AdCreateRequest
import ru.otus.otuskotlin.marketplace.api.v2.models.AdUpdateRequest
import ru.otus.otuskotlin.marketplace.common.helpers.errorMapping
import ru.otus.otuskotlin.marketplace.mappers.v2.MapContext

internal fun ICorAddExecDsl<MapContext>.takeTitle(title: String) = worker {
    this.title = title
    on { state == MapContext.MapState.RUNNING }
    handle {
        when(val r = req) {
            is AdCreateRequest -> adResult.title = r.ad?.title ?: ""
            is AdUpdateRequest -> adResult.title = r.ad?.title ?: ""
            else -> errorMapping(
                field = "title",
                violationCode = "wrongRequest",
                description = "invalid request contains no title"
            )
        }
    }
}
