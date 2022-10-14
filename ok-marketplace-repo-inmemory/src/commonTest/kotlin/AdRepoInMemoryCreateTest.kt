package ru.otus.otuskotlin.marketplace.repo.inmemory

import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoAdCreateTest

class AdRepoInMemoryCreateTest : RepoAdCreateTest() {
    override val repo = AdRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew.asString() }
    )
}
