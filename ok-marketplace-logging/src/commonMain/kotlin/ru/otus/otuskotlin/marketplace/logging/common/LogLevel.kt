package ru.otus.otuskotlin.marketplace.logging.common

enum class LogLevel(
    private val levelInt: Int,
    private val levelStr: String,
) {
    ERROR(40, "ERROR"),
    WARN(30, "WARN"),
    INFO(20, "INFO"),
    DEBUG(10, "DEBUG"),
    TRACE(0, "TRACE");

    fun toInt(): Int {
        return levelInt
    }

    /**
     * Returns the string representation of this Level.
     */
    override fun toString(): String {
        return levelStr
    }
}
