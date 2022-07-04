package ru.otus.otuskotlin.oop

import org.junit.Test
import java.util.Arrays
import kotlin.test.assertEquals

enum class SimpleEnum {
    LOW,
    HIGH
}

enum class EnumWithData(val level: Int, val description: String) {
    LOW(10, "low level"),
    HIGH(20, "high level")
}


enum class MyEnum : Iterable<MyEnum> {
    FOO {
        override fun doSmth() {
            println("do foo")
        }
    },

    BAR {
        override fun doSmth() {
            println("do bar")
        }
    };

    abstract fun doSmth()

    override fun iterator(): Iterator<MyEnum> = listOf(FOO, BAR).iterator()
}

class EnumsTest {
    @Test
    fun test() {
        var e = SimpleEnum.LOW;
        println(e)

        e = SimpleEnum.valueOf("HIGH")
        println(e)
        println(e.ordinal)
        assertEquals(e.ordinal, 1)

        println(SimpleEnum.values().contentToString())
    }
}
