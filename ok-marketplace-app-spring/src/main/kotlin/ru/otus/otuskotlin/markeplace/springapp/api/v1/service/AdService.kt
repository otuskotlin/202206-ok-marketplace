package ru.otus.otuskotlin.markeplace.springapp.api.v1.service

import marketplace.stubs.Bolt
import org.springframework.stereotype.Service
import ru.otus.otuskotlin.markeplace.springapp.api.v1.errorResponse
import ru.otus.otuskotlin.markeplace.springapp.api.v1.successResponse
import ru.otus.otuskotlin.markeplace.springapp.common.notFoundError
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplAdId
import ru.otus.otuskotlin.marketplace.common.models.MkplVisibility
import ru.otus.otuskotlin.marketplace.common.models.MkplWorkMode
import ru.otus.otuskotlin.marketplace.common.stubs.MkplStubs

@Service
class AdService {

    fun createAd(mpContext: MkplContext): MkplContext {
        val response = when (mpContext.workMode) {
            MkplWorkMode.PROD -> TODO()
            MkplWorkMode.TEST -> mpContext.adRequest
            MkplWorkMode.STUB -> Bolt.getModel()
        }
        return mpContext.successResponse {
            adResponse = response
        }
    }

    fun readAd(mpContext: MkplContext): MkplContext {
        val requestedId = mpContext.adRequest.id

        return when (mpContext.stubCase) {
            MkplStubs.SUCCESS -> mpContext.successResponse {
                adResponse = Bolt.getModel().apply { id = requestedId }
            }
            else -> mpContext.errorResponse {
                it.copy(field = "ad.id", message = notFoundError(requestedId.asString()))
            }
        }
    }

    fun updateAd(context: MkplContext) = when (context.stubCase) {
        MkplStubs.SUCCESS -> context.successResponse {
            adResponse = Bolt.getModel {
                if (adRequest.visibility != MkplVisibility.NONE) visibility = adRequest.visibility
                if (adRequest.id != MkplAdId.NONE) id = adRequest.id
                if (adRequest.title.isNotEmpty()) title = adRequest.title
            }
        }
        else -> context.errorResponse {
            it.copy(field = "ad.id", message = notFoundError(context.adRequest.id.asString()))
        }
    }


    fun deleteAd(context: MkplContext): MkplContext = when (context.stubCase) {
        MkplStubs.SUCCESS -> context.successResponse {
            adResponse = Bolt.getModel { id = context.adRequest.id }
        }
        else -> context.errorResponse {
            it.copy(
                field = "ad.id",
                message = notFoundError(context.adRequest.id.asString())
            )
        }
    }

    fun searchAd(context: MkplContext): MkplContext {
        val filter = context.adFilterRequest

        val searchableString = filter.searchString

        return when(context.stubCase) {
            MkplStubs.SUCCESS -> context.successResponse {
                adsResponse.addAll(
                    Bolt.getModels()
                )
            }
            else -> context.errorResponse {
                it.copy(
                    message = "Nothing found by $searchableString"
                )
            }
        }
    }
}

