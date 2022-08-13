package ru.otus.otuskotlin.markeplace.springapp.api.v1.service

import marketplace.stubs.Bolt
import org.springframework.stereotype.Service
import ru.otus.otuskotlin.markeplace.springapp.api.v1.errorResponse
import ru.otus.otuskotlin.markeplace.springapp.api.v1.successResponse
import ru.otus.otuskotlin.markeplace.springapp.common.notFoundError
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.stubs.MkplStubs

@Service
class OfferService {

    fun searchOffers(context: MkplContext): MkplContext {
        val request = context.adRequest

        return when (context.stubCase) {
            MkplStubs.SUCCESS -> context.successResponse {
                adsResponse.addAll(Bolt.getModels().onEach { it.id = request.id })
            }
            else -> {
                context.errorResponse {
                    it.copy(field = "ad.id", message = notFoundError(request.id.asString()))
                }
            }
        }
    }
}