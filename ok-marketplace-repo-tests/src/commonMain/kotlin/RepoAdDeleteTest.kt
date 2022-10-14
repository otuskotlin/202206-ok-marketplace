package ru.otus.otuskotlin.marketplace.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplAdId
import ru.otus.otuskotlin.marketplace.common.repo.DbAdIdRequest
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdDeleteTest {
    abstract val repo: IAdRepository

    @Test
    fun deleteSuccess() = runTest {
        val result = repo.deleteAd(DbAdIdRequest(successId, lock = lockOld))

        assertEquals(true, result.isSuccess)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockOld, result.data?.lock)
    }

    @Test
    fun deleteNotFound() = runTest {
        val result = repo.readAd(DbAdIdRequest(notFoundId, lock = lockOld))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun deleteConcurrency() = runTest {
        val result = repo.deleteAd(DbAdIdRequest(concurrencyId, lock = lockBad))

        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(lockOld, result.data?.lock)
    }

    companion object : BaseInitAds("delete") {
        override val initObjects: List<MkplAd> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteLock"),
        )
        val successId = MkplAdId(initObjects[0].id.asString())
        val notFoundId = MkplAdId("ad-repo-delete-notFound")
        val concurrencyId = MkplAdId(initObjects[1].id.asString())
    }
}
