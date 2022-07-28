package ru.otus.otuskotlin.marketplace.user.models

import java.time.LocalDateTime

enum class Action {
    READ,
    DELETE,
    WRITE,
    ADD,
    UPDATE,
    CREATE
}

data class User(
    val id: String,

    val firstName: String,
    val secondName: String,
    val lastName: String,

    val phone: String,
    val email: String,

    val actions: Set<Action>,

    val available: List<LocalDateTime>,
)
