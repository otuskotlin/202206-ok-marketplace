package ru.otus.otuskotlin.marketplace.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplAdId
import ru.otus.otuskotlin.marketplace.common.repo.DbAdIdRequest
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdReadTest {
    abstract val repo: IAdRepository

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readAd(DbAdIdRequest(successId))

        assertEquals(true, result.isSuccess)
        assertEquals(readSuccessStub, result.data)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readAd(DbAdIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitAds("delete") {
        override val initObjects: List<MkplAd> = listOf(
            createInitTestModel("read")
        )
        private val readSuccessStub = initObjects.first()

        val successId = MkplAdId(readSuccessStub.id.asString())
        val notFoundId = MkplAdId("ad-repo-read-notFound")

    }
}
