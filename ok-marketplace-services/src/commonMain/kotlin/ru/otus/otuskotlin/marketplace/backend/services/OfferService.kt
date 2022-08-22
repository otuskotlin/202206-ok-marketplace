package ru.otus.otuskotlin.marketplace.backend.services

import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplError
import ru.otus.otuskotlin.marketplace.common.stubs.MkplStubs

class OfferService {
    fun searchOffers(context: MkplContext, buildError: () -> MkplError): MkplContext {
        val request = context.adRequest

        return when (context.stubCase) {
            MkplStubs.SUCCESS -> context.successResponse {
                adsResponse.add(MkplAdStub.get())
            }
            else -> {
                context.errorResponse(buildError) {
                    it.copy(field = "ad.id", message = notFoundError(request.id.asString()))
                }
            }
        }
    }
}
