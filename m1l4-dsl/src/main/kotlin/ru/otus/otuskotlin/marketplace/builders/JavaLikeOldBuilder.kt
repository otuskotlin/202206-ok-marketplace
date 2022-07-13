@file:Suppress("PackageDirectoryMismatch")
package ru.otus.otuskotlin.marketplace.builders.java

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
    private var eggs = 0
    private var bacon = false
    private var title = ""
    private var drink = Drink.WATER

    fun withEggs(eggs: Int): BreakfastBuilder {
        this.eggs = eggs
        return this
    }

    fun withBacon(value: Boolean): BreakfastBuilder {
        this.bacon = value
        return this
    }

    fun withTitle(title: String): BreakfastBuilder {
        this.title = title
        return this
    }

    fun withDrink(value: Drink): BreakfastBuilder {
        this.drink = value
        return this
    }

    fun build() = Meal.Breakfast(eggs, bacon, drink, title)
}

fun main() {
    val breakfast = BreakfastBuilder()
        .withEggs(3)
        .withBacon(true)
        .withTitle("Simple")
        .withDrink(Drink.COFFEE)
        .build()

    println(breakfast)
}
