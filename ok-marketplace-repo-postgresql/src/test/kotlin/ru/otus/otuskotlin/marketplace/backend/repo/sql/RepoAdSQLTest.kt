package ru.otus.otuskotlin.marketplace.backend.repo.sql

import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoAdCreateTest
import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoAdDeleteTest
import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoAdReadTest
import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoAdSearchTest
import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoAdUpdateTest
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository

class RepoAdSQLCreateTest : RepoAdCreateTest() {
    override val repo: IAdRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}

class RepoAdSQLDeleteTest : RepoAdDeleteTest() {
    override val repo: IAdRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdSQLReadTest : RepoAdReadTest() {
    override val repo: IAdRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdSQLSearchTest : RepoAdSearchTest() {
    override val repo: IAdRepository = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoAdSQLUpdateTest : RepoAdUpdateTest() {
    override val repo: IAdRepository = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}
