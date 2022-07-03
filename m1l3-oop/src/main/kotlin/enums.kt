package ru.otus.otuskotlin.oop

import java.util.Arrays

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

fun main() {
    var e = SimpleEnum.LOW;
    println(e)

    e = SimpleEnum.valueOf("HIGH")
    println(e)
    println(e.ordinal)

    println(SimpleEnum.values().contentToString())
}