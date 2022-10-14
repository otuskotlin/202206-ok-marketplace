package ru.otus.otuskotlin.marketplace.repo.inmemory

import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoAdReadTest
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository

class AdRepoInMemoryReadTest: RepoAdReadTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
