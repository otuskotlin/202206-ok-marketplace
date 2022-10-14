package ru.otus.otuskotlin.marketplace.repo.inmemory

import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoAdUpdateTest
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository

class AdRepoInMemoryUpdateTest : RepoAdUpdateTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
