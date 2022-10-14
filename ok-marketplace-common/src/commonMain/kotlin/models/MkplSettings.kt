package ru.otus.otuskotlin.marketplace.common.models

import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository

data class MkplSettings(
    val repoStub: IAdRepository = IAdRepository.NONE,
    val repoTest: IAdRepository = IAdRepository.NONE,
    val repoProd: IAdRepository = IAdRepository.NONE,
)
