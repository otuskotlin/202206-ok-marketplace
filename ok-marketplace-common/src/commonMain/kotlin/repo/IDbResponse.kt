package ru.otus.otuskotlin.marketplace.common.repo

import ru.otus.otuskotlin.marketplace.common.models.MkplError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<MkplError>
}
