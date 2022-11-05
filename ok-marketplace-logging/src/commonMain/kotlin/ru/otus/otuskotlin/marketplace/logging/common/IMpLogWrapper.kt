package ru.otus.otuskotlin.marketplace.logging.common

import kotlinx.datetime.Clock

interface IMpLogWrapper {
    val loggerId: String

    fun log(
        msg: String = "",
        level: LogLevel = LogLevel.TRACE,
        marker: String = "DEV",
        e: Throwable? = null,
        data: Any? = null,
        vararg objs: Pair<String, Any>?
    )

    fun error(
        msg: String = "",
        marker: String = "DEV",
        e: Throwable? = null,
        data: Any? = null,
        vararg objs: Pair<String, Any>?
    ) = log(msg, LogLevel.ERROR, marker, e, data, *objs)

    fun info(
        msg: String = "",
        marker: String = "DEV",
        data: Any? = null,
        vararg objs: Pair<String, Any>?
    ) = log(msg, LogLevel.INFO, marker, null, data, *objs)

    fun debug(
        msg: String = "",
        marker: String = "DEV",
        data: Any? = null,
        vararg objs: Pair<String, Any>?
    ) = log(msg, LogLevel.DEBUG, marker, null, data, *objs)

    /**
     * Функция обертка для выполнения прикладного кода с логированием перед выполнением и после
     */
    suspend fun <T> doWithLogging(
        id: String = "",
        level: LogLevel = LogLevel.INFO,
        block: suspend () -> T,
    ): T = try {
        val timeStart = Clock.System.now()
        log("$loggerId Entering $id", level)
        block().also {
            val diffTime = Clock.System.now().toEpochMilliseconds() - timeStart.toEpochMilliseconds()
            log(
                msg = "$loggerId Finishing $id",
                level = level,
                objs = arrayOf(Pair("metricHandleTime", diffTime))
            )
        }
    } catch (e: Throwable) {
        log(
            msg = "$loggerId Failing $id",
            level = LogLevel.ERROR,
            e = e)
        throw e
    }

    /**
     * Функция обертка для выполнения прикладного кода с логированием ошибки
     */
    suspend fun <T> doWithErrorLogging(
        id: String = "",
        throwRequired: Boolean = true,
        block: suspend () -> T,
    ): T? = try {
        val result = block()
        result
    } catch (e: Throwable) {
        log(
            msg = "$loggerId Failing $id",
            level = LogLevel.ERROR,
            e = e)
        if (throwRequired) throw e else null
    }
}
