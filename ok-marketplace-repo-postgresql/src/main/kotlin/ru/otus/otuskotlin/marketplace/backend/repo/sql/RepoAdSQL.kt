package ru.otus.otuskotlin.marketplace.backend.repo.sql

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import ru.otus.otuskotlin.marketplace.common.helpers.errorRepoConcurrency
import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplAdId
import ru.otus.otuskotlin.marketplace.common.models.MkplAdLock
import ru.otus.otuskotlin.marketplace.common.models.MkplDealSide
import ru.otus.otuskotlin.marketplace.common.models.MkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplUserId
import ru.otus.otuskotlin.marketplace.common.repo.DbAdFilterRequest
import ru.otus.otuskotlin.marketplace.common.repo.DbAdIdRequest
import ru.otus.otuskotlin.marketplace.common.repo.DbAdRequest
import ru.otus.otuskotlin.marketplace.common.repo.DbAdResponse
import ru.otus.otuskotlin.marketplace.common.repo.DbAdsResponse
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository
import java.sql.SQLException
import java.util.*

private const val notFoundCode = "not-found"

class RepoAdSQL(
    url: String = "jdbc:postgresql://localhost:5432/marketplacedevdb",
    user: String = "postgres",
    password: String = "marketplace-pass",
    schema: String = "marketplace",
    initObjects: Collection<MkplAd> = emptyList(),
) : IAdRepository {
    private val db by lazy { SqlConnector(url, user, password, schema).connect(AdsTable, UsersTable) }
    private val mutex = Mutex()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(item: MkplAd): DbAdResponse {
        return safeTransaction({
            val realOwnerId = UsersTable.insertIgnore {
                if (item.ownerId != MkplUserId.NONE) {
                    it[id] = item.ownerId.asString()
                }
            } get UsersTable.id

            val res = AdsTable.insert {
                if (item.id != MkplAdId.NONE) {
                    it[id] = item.id.asString()
                }
                it[title] = item.title
                it[description] = item.description
                it[ownerId] = realOwnerId
                it[visibility] = item.visibility
                it[adType] = item.adType
                it[lock] = item.lock.asString()
            }

            DbAdResponse(AdsTable.from(res), true)
        }, {
            DbAdResponse(
                data = null,
                isSuccess = false,
                errors = listOf(MkplError(message = message ?: localizedMessage))
            )
        })
    }

    override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        val ad = rq.ad.copy(lock = MkplAdLock(UUID.randomUUID().toString()))
        return mutex.withLock {
            save(ad)
        }
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse {
        return safeTransaction({
            val result = (AdsTable innerJoin UsersTable).select { AdsTable.id.eq(rq.id.asString()) }.single()

            DbAdResponse(AdsTable.from(result), true)
        }, {
            val err = when (this) {
                is NoSuchElementException -> MkplError(field = "id", message = "Not Found", code = notFoundCode)
                is IllegalArgumentException -> MkplError(message = "More than one element with the same id")
                else -> MkplError(message = localizedMessage)
            }
            DbAdResponse(data = null, isSuccess = false, errors = listOf(err))
        })
    }

    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
        val key = rq.ad.id.takeIf { it != MkplAdId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.ad.lock.takeIf { it != MkplAdLock.NONE }?.asString()
        val newAd = rq.ad.copy(lock = MkplAdLock(UUID.randomUUID().toString()))

        return mutex.withLock {
            safeTransaction({
                val local = AdsTable.select { AdsTable.id.eq(key) }.singleOrNull()?.let {
                    AdsTable.from(it)
                } ?: return@safeTransaction resultErrorNotFound

                return@safeTransaction when (oldLock) {
                    null, local.lock.asString() -> updateDb(newAd)
                    else -> resultErrorConcurrent(local.lock.asString(), local)
                }
            }, {
                DbAdResponse(
                    data = rq.ad,
                    isSuccess = false,
                    errors = listOf(MkplError(field = "id", message = "Not Found", code = notFoundCode))
                )
            })
        }
    }

    private fun updateDb(newAd: MkplAd): DbAdResponse {
        UsersTable.insertIgnore {
            if (newAd.ownerId != MkplUserId.NONE) {
                it[id] = newAd.ownerId.asString()
            }
        }

        AdsTable.update({ AdsTable.id.eq(newAd.id.asString()) }) {
            it[title] = newAd.title
            it[description] = newAd.description
            it[ownerId] = newAd.ownerId.asString()
            it[visibility] = newAd.visibility
            it[adType] = newAd.adType
        }
        val result = AdsTable.select { AdsTable.id.eq(newAd.id.asString()) }.single()

        return DbAdResponse(data = AdsTable.from(result), isSuccess = true)
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse {
        val key = rq.id.takeIf { it != MkplAdId.NONE }?.asString() ?: return resultErrorEmptyId

        return mutex.withLock {
            safeTransaction({
                val local = AdsTable.select { AdsTable.id.eq(key) }.single().let { AdsTable.from(it) }
                if (local.lock == rq.lock) {
                    AdsTable.deleteWhere { AdsTable.id eq rq.id.asString() }
                    DbAdResponse(data = local, isSuccess = true)
                } else {
                    resultErrorConcurrent(rq.lock.asString(), local)
                }
            }, {
                DbAdResponse(
                    data = null,
                    isSuccess = false,
                    errors = listOf(MkplError(field = "id", message = "Not Found"))
                )
            })
        }
    }

    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse {
        return safeTransaction({
            // Select only if options are provided
            val results = (AdsTable innerJoin UsersTable).select {
                (if (rq.ownerId == MkplUserId.NONE) Op.TRUE else AdsTable.ownerId eq rq.ownerId.asString()) and
                        (
                                if (rq.titleFilter.isBlank()) Op.TRUE else (AdsTable.title like "%${rq.titleFilter}%") or
                                        (AdsTable.description like "%${rq.titleFilter}%")
                                ) and
                        (if (rq.dealSide == MkplDealSide.NONE) Op.TRUE else AdsTable.adType eq rq.dealSide)
            }

            DbAdsResponse(data = results.map { AdsTable.from(it) }, isSuccess = true)
        }, {
            DbAdsResponse(data = emptyList(), isSuccess = false, listOf(MkplError(message = localizedMessage)))
        })
    }

    /**
     * Transaction wrapper to safely handle caught exception and throw all sql-like exceptions. Also remove lot's of duplication code
     */
    private fun <T> safeTransaction(statement: Transaction.() -> T, handleException: Throwable.() -> T): T {
        return try {
            transaction(db, statement)
        } catch (e: SQLException) {
            throw e
        } catch (e: Throwable) {
            return handleException(e)
        }
    }

    companion object {
        val resultErrorEmptyId = DbAdResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                MkplError(
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )

        fun resultErrorConcurrent(lock: String, ad: MkplAd?) = DbAdResponse(
            data = ad,
            isSuccess = false,
            errors = listOf(
                errorRepoConcurrency(MkplAdLock(lock), ad?.lock?.let { MkplAdLock(it.asString()) }
                )
            ))

        val resultErrorNotFound = DbAdResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                MkplError(
                    field = "id",
                    message = "Not Found",
                    code = notFoundCode
                )
            )
        )
    }
}
