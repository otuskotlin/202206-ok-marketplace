package ru.otus.otuskotlin.marketplace.common.models

interface IClientSession<T> {
    val fwSession: T

    companion object {
        val NONE = object : IClientSession<Unit> {
            override val fwSession: Unit = Unit
        }
    }
}
