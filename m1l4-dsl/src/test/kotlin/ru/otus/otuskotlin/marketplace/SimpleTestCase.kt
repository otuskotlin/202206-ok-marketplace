package ru.otus.otuskotlin.marketplace

import org.junit.Test

class SimpleTestCase {
    @Test
    fun `my test`() {
        sout {
            1 + 123
        }
    }

    @Test
    fun `sout with prefix`() {
        soutWithTimestamp {
            "${time()}: my line."
        }
    }
}
