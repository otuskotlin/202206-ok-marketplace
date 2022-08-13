package ru.otus.otuskotlin.marketplace.common.models

@JvmInline
value class MkplAdId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = MkplAdId("")
    }
}
