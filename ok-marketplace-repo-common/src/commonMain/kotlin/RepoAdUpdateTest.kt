package ru.otus.otuskotlin.marketplace.backend.repo.common

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.repo.DbAdRequest
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals


abstract class RepoAdUpdateTest {
    abstract val repo: IAdRepository

    @Test
    fun updateSuccess() {
        val result = runBlocking { repo.updateAd(DbAdRequest(updateObj)) }
        assertEquals(true, result.isSuccess)
        assertEquals(updateObj.id, result.data?.id)
        assertEquals(updateObj.title, result.data?.title)
        assertEquals(updateObj.description, result.data?.description)
        assertEquals(updateObj.adType, result.data?.adType)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() {
        val result = runBlocking { repo.updateAd(DbAdRequest(updateObjNotFound)) }
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        assertEquals(listOf(MkplError(field = "id", message = "Not Found")), result.errors)
    }

    companion object: BaseInitAds("update") {
        override val initObjects: List<MkplAd> = listOf(
            createInitTestModel("update")
        )
        private val updateId = initObjects.first().id
        private val updateIdNotFound = MkplAdId("ad-repo-update-not-found")

        private val updateObj = MkplAd(
            id = updateId,
            title = "update object",
            description = "update object description",
            ownerId = MkplUserId("owner-123"),
            visibility = MkplVisibility.VISIBLE_TO_GROUP,
            adType = MkplDealSide.SUPPLY,
        )

        private val updateObjNotFound = MkplAd(
            id = updateIdNotFound,
            title = "update object not found",
            description = "update object not found description",
            ownerId = MkplUserId("owner-123"),
            visibility = MkplVisibility.VISIBLE_TO_GROUP,
            adType = MkplDealSide.SUPPLY,
        )
    }
}
