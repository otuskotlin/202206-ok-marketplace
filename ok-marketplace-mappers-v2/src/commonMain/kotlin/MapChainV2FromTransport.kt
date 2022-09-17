package ru.otus.otuskotlin.marketplace.mappers.v2

import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.kotlin.cor.rootChain
import ru.otus.otuskotlin.marketplace.api.v2.models.*
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.mappers.v2.workers.*
import ru.otus.otuskotlin.marketplace.mappers.v2.workers.takeDescription
import ru.otus.otuskotlin.marketplace.mappers.v2.workers.takeId
import ru.otus.otuskotlin.marketplace.mappers.v2.workers.takeSearchString
import ru.otus.otuskotlin.marketplace.mappers.v2.workers.takeTitle

@Suppress("unused")
suspend fun MkplContext.chainFromTransport(request: IRequest) {
    val mapCtx = MapContext(req = request)
    CreateTransportChain.chain.exec(mapCtx)
    adRequest = mapCtx.adResult
    adFilterRequest = mapCtx.adFilterResult
    errors.addAll(mapCtx.errors)
    if (mapCtx.state == MapContext.MapState.FAILING)
        state = MkplState.FAILING
}

private object CreateTransportChain {
    val chain = rootChain<MapContext> {
        worker {
            title = "Init state"
            on { state == MapContext.MapState.NONE }
            handle { state = MapContext.MapState.RUNNING }
        }

        chain {
            title = "Mapping Create request"
            on { req is AdCreateRequest }
            takeTitle("Extracting Title from Request")
            takeDescription("Extracting Description from Request")
        }
        chain {
            title = "Mapping Read request"
            on { req is AdReadRequest }
            takeId("Extracting Id from Request")
        }
        chain {
            title = "Mapping Update request"
            on { req is AdUpdateRequest }
            takeId("Extracting Id from Request")
            takeLock("Extracting Id from Request")
            takeTitle("Extracting Title from Request")
            takeDescription("Extracting Description from Request")
        }
        chain {
            title = "Mapping Delete request"
            on { req is AdDeleteRequest }
            takeId("Extracting Id from Request")
            takeLock("Extracting Id from Request")
        }
        chain {
            title = "Mapping Search request"
            on { req is AdSearchRequest }
            takeSearchString("Extracting Search String from Request")
        }
        chain {
            title = "Mapping Offers request"
            on { req is AdOffersRequest }
            takeId("Extracting Id from Request")
        }
    }.build()
}

