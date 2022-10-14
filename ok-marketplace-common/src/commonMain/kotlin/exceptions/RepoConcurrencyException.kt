package ru.otus.otuskotlin.marketplace.common.exceptions

import ru.otus.otuskotlin.marketplace.common.models.MkplAdLock

class RepoConcurrencyException(expectedLock: MkplAdLock, actualLock: MkplAdLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
