import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.withLock
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.model.AdEntity
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.repo.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class AdRepoInMemory(
    initObjects: List<MkplAd> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() }
) : IAdRepository {
    /**
     * Инициализация кеша с установкой "времени жизни" данных после записи
     */
    private val cache = Cache.Builder()
        .expireAfterWrite(ttl)
        .build<String, AdEntity>()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(ad: MkplAd) {
        val entity = AdEntity(ad)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id, entity)
    }

    override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        val key = randomUuid()
        val ad = rq.ad.copy(id = MkplAdId(key), lock = MkplAdLock(randomUuid()))
        val entity = AdEntity(ad)
        cache.put(key, entity)
        return DbAdResponse(
            data = ad,
            isSuccess = true,
        )
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse {
        val key = rq.id.takeIf { it != MkplAdId.NONE }?.asString() ?: return resultErrorEmptyId
        return cache.get(key)
            ?.let {
                DbAdResponse(
                    data = it.toInternal(),
                    isSuccess = true,
                )
            } ?: resultErrorNotFound
    }

    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
        val key = rq.ad.id.takeIf { it != MkplAdId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.ad.lock.takeIf { it != MkplAdLock.NONE }?.asString()
        val newAd = rq.ad.copy(lock = MkplAdLock(randomUuid()))
        val entity = AdEntity(newAd)
        val oldAd = cache.get(key)
        oldAd?.let { cache.put(key, entity) } ?: return resultErrorNotFound
        return DbAdResponse(
            data = newAd,
            isSuccess = true,
        )
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse {
        val key = rq.id.takeIf { it != MkplAdId.NONE }?.asString() ?: return resultErrorEmptyId
        cache.invalidate(key)
        return DbAdResponse(
            data = null,
            isSuccess = true,
            errors = emptyList()
        )
    }

    /**
     * Поиск объявлений по фильтру
     * Если в фильтре не установлен какой-либо из параметров - по нему фильтрация не идет
     */
    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse {
        val result = cache.asMap().asSequence()
            .filter { entry ->
                rq.ownerId.takeIf { it != MkplUserId.NONE }?.let {
                    it.asString() == entry.value.ownerId
                } ?: true
            }
            .filter { entry ->
                rq.dealSide.takeIf { it != MkplDealSide.NONE }?.let {
                    it.name == entry.value.adType
                } ?: true
            }
            .filter { entry ->
                rq.titleFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.title?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        return DbAdsResponse(
            data = result,
            isSuccess = true
        )
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
        val resultErrorNotFound = DbAdResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                MkplError(
                    field = "id",
                    message = "Not Found"
                )
            )
        )
    }
}
