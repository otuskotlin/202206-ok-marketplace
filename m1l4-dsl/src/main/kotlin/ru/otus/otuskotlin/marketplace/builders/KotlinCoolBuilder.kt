@file:Suppress("PackageDirectoryMismatch")
package ru.otus.otuskotlin.marketplace.builders.kotlin

enum class Drink {
    WATER,
    COFFEE,
    TEA
}

abstract class Meal {
    data class Breakfast(
        val eggs: Int,
        val bacon: Boolean,
        val drink: Drink,
        val title: String
    ) : Meal()
}

class BreakfastBuilder {
    var eggs = 0
    var bacon = false
    var title = ""
    var drink = Drink.WATER

    fun build() = Meal.Breakfast(eggs, bacon, drink, title)
}

fun breakfast(block: BreakfastBuilder.() -> Unit): Meal.Breakfast {
    return BreakfastBuilder().apply(block).build()
}

fun main() {
    val myBreakfast = breakfast {
        eggs = 3 // BreakfastBuilder().eggs = 3
        bacon = true
        title = "Simple"
        drink = Drink.COFFEE
    }

    println(myBreakfast)
}
