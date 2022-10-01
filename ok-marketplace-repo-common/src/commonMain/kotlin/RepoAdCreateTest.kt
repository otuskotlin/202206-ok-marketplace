package ru.otus.otuskotlin.marketplace.backend.repo.common

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.repo.DbAdRequest
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


abstract class RepoAdCreateTest {
    abstract val repo: IAdRepository

    @Test
    fun createSuccess() {
        val result = runBlocking { repo.createAd(DbAdRequest(createObj)) }
        val expected = createObj.copy(id = result.data?.id ?: MkplAdId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.title, result.data?.title)
        assertEquals(expected.description, result.data?.description)
        assertEquals(expected.adType, result.data?.adType)
        assertNotEquals(MkplAdId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
    }

    companion object: BaseInitAds("search") {

        private val createObj = MkplAd(
            title = "create object",
            description = "create object description",
            ownerId = MkplUserId("owner-123"),
            visibility = MkplVisibility.VISIBLE_TO_GROUP,
            adType = MkplDealSide.SUPPLY,
        )
        override val initObjects: List<MkplAd> = emptyList()
    }
}
