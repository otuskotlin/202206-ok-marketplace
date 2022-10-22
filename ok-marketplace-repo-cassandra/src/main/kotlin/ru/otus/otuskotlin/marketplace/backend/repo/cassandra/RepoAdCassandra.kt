package ru.otus.otuskotlin.marketplace.backend.repo.cassandra

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.future.await
import kotlinx.coroutines.withTimeout
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.marketplace.backend.repo.cassandra.model.AdCassandraDTO
import ru.otus.otuskotlin.marketplace.common.helpers.asMkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplAdId
import ru.otus.otuskotlin.marketplace.common.models.MkplAdLock
import ru.otus.otuskotlin.marketplace.common.models.MkplError
import ru.otus.otuskotlin.marketplace.common.repo.*
import java.util.concurrent.CompletionStage

class RepoAdCassandra(
    private val dao: AdCassandraDAO,
    private val timeoutMillis: Long = 300_000,
    private val randomUuid: () -> String = { uuid4().toString() }
) : IAdRepository {
    private val log = LoggerFactory.getLogger(javaClass)

    private fun errorToAdResponse(e: Exception) = DbAdResponse.error(e.asMkplError())
    private fun errorToAdsResponse(e: Exception) = DbAdsResponse.error(e.asMkplError())

    private suspend inline fun <DbRes, Response> doDbAction(
        name: String,
        crossinline daoAction: () -> CompletionStage<DbRes>,
        okToResponse: (DbRes) -> Response,
        errorToResponse: (Exception) -> Response
    ): Response = doDbAction(
        name,
        {
            val dbRes = withTimeout(timeoutMillis) { daoAction().await() }
            okToResponse(dbRes)
        },
        errorToResponse
    )

    private suspend inline fun readAndDoDbAction(
        name: String,
        id: MkplAdId,
        successResult: MkplAd?,
        daoAction: () -> CompletionStage<Boolean>,
        errorToResponse: (Exception) -> DbAdResponse
    ): DbAdResponse =
        if (id == MkplAdId.NONE)
            ID_IS_EMPTY
        else doDbAction(
            name,
            {
                val read = dao.read(id.asString()).await()
                if (read == null) ID_NOT_FOUND
                else {
                    val success = daoAction().await()
                    if (success) DbAdResponse.success(successResult ?: read.toAdModel())
                    else DbAdResponse(
                        read.toAdModel(),
                        false,
                        CONCURRENT_MODIFICATION.errors
                    )
                }
            },
            errorToResponse
        )

    private inline fun <Response> doDbAction(
        name: String,
        daoAction: () -> Response,
        errorToResponse: (Exception) -> Response
    ): Response =
        try {
            daoAction()
        } catch (e: Exception) {
            log.error("Failed to $name", e)
            errorToResponse(e)
        }

    override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        val new = rq.ad.copy(id = MkplAdId(randomUuid()), lock = MkplAdLock(randomUuid()))
        return doDbAction(
            "create",
            { dao.create(AdCassandraDTO(new)) },
            { DbAdResponse.success(new) },
            ::errorToAdResponse
        )
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse =
        if (rq.id == MkplAdId.NONE)
            ID_IS_EMPTY
        else doDbAction(
            "read",
            { dao.read(rq.id.asString()) },
            { found ->
                if (found != null) DbAdResponse.success(found.toAdModel())
                else ID_NOT_FOUND
            },
            ::errorToAdResponse
        )

    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
        val prevLock = rq.ad.lock.asString()
        val new = rq.ad.copy(lock = MkplAdLock(randomUuid()))
        val dto = AdCassandraDTO(new)

        return readAndDoDbAction(
            "update",
            rq.ad.id,
            new,
            { dao.update(dto, prevLock) },
            ::errorToAdResponse
            )
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse =
        readAndDoDbAction(
            "delete",
            rq.id,
            null,
            { dao.delete(rq.id.asString(), rq.lock.asString()) },
            ::errorToAdResponse
        )


    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse =
        doDbAction(
            "search",
            { dao.search(rq) },
            { found ->
                DbAdsResponse.success(found.map { it.toAdModel() })
            },
            ::errorToAdsResponse
        )

    companion object {
        private val ID_IS_EMPTY = DbAdResponse.error(MkplError(field = "id", message = "Id is empty"))
        private val ID_NOT_FOUND =
            DbAdResponse.error(MkplError(field = "id", code = "not-found", message = "Not Found"))
        private val CONCURRENT_MODIFICATION =
            DbAdResponse.error(MkplError(field = "lock", code = "concurrency", message = "Concurrent modification"))
    }
}