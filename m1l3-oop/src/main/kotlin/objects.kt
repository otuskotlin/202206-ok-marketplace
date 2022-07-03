package ru.otus.otuskotlin.oop

class ObjectsExample {
    companion object {
        init {
            println("companion inited") // инициализация при загрузке класса ObjectsExample
        }
        fun doSmth() {
            println("companion object")
        }
    }

    object A {
        init {
            println("A inited") // ленивая инициализация при первом обращении к А
        }
        fun doSmth() {
            println("object A")
        }
    }
}

fun main() {
    ObjectsExample()
    ObjectsExample.doSmth()
    ObjectsExample.A.doSmth()
    ObjectsExample.A.doSmth()
}