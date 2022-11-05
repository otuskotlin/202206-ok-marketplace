package ru.otus.otuskotlin.marketplace.logging.common

import kotlin.reflect.KClass

expect fun mpLogger(loggerId: String): IMpLogWrapper

expect fun mpLogger(cls: KClass<*>): IMpLogWrapper

