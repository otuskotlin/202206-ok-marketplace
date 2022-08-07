package ru.otus.otuskotlin.marketplace.common.models

@JvmInline
value class MkplProductId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = MkplProductId("")
    }
}