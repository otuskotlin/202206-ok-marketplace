package ru.otus.otuskotlin.oop

import org.junit.Test
import kotlin.test.assertEquals

sealed interface Base

class ChildA : Base

class ChildB : Base

// Uncomment this to get compilation error
//class ChildC : Base

class ObjectsTest {
    @Test
    fun test() {
        val obj: Base = ChildA()

        val result = when (obj) {
            is ChildA -> "a"
            is ChildB -> "b"
        }

        println(result)
        assertEquals(result, "a")
    }
}