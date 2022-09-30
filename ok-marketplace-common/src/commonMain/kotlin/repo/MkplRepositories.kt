package ru.otus.otuskotlin.marketplace.common.repo

data class MkplRepositories(
    val prod: IAdRepository = IAdRepository.NONE,
    val test: IAdRepository = IAdRepository.NONE,
) {
    companion object {
        val NONE = MkplRepositories()
    }
}
