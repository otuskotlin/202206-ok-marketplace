package ru.otus.otuskotlin.marketplace.backend.repo.common

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplAdId
import ru.otus.otuskotlin.marketplace.common.models.MkplError
import ru.otus.otuskotlin.marketplace.common.repo.DbAdIdRequest
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals


abstract class RepoAdDeleteTest {
    abstract val repo: IAdRepository

    @Test
    fun deleteSuccess() {
        val result = runBlocking { repo.deleteAd(DbAdIdRequest(successId)) }

        assertEquals(true, result.isSuccess)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun deleteNotFound() {
        val result = runBlocking { repo.readAd(DbAdIdRequest(notFoundId)) }

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        assertEquals(
            listOf(MkplError(field = "id", message = "Not Found")),
            result.errors
        )
    }

    companion object: BaseInitAds("delete") {
        override val initObjects: List<MkplAd> = listOf(
            createInitTestModel("delete")
        )
        private val deleteSuccessStub = initObjects.first()
        val successId = MkplAdId(deleteSuccessStub.id.asString())
        val notFoundId = MkplAdId("ad-repo-delete-notFound")
    }
}
