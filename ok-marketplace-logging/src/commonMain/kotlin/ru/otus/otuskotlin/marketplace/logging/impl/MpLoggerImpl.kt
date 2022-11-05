package ru.otus.otuskotlin.marketplace.logging.impl

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.StaticConfig
import ru.otus.otuskotlin.marketplace.logging.common.IMpLogWrapper
import kotlin.reflect.KClass

fun mpLoggerCommon(loggerId: String): IMpLogWrapper {
    val logger = Logger(
        config = StaticConfig(
            minSeverity = Severity.Info,
        ),
        tag = "DEV"
    )
    return MpLoggerWrapperCommon(
        logger = logger,
        loggerId = loggerId,
    )
}

fun mpLoggerCommon(cls: KClass<*>): IMpLogWrapper {
    val logger = Logger(
        config = StaticConfig(
            minSeverity = Severity.Info,
        ),
        tag = "DEV"
    )
    return MpLoggerWrapperCommon(
        logger = logger,
        loggerId = cls.qualifiedName?: "",
    )
}
