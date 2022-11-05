package ru.otus.otuskotlin.marketplace.logging.jvm

import ch.qos.logback.classic.Logger
import ru.otus.otuskotlin.marketplace.logging.common.IMpLogWrapper

/**
 * Generate internal MpLogContext logger
 *
 * @param logger Logback instance from [LoggerFactory.getLogger()]
 */
fun mpLoggerJvm(logger: Logger): IMpLogWrapper = MpLogWrapperJvm(
    logger = logger,
    loggerId = logger.name,
)
