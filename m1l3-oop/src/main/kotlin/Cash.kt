package ru.otus.otuskotlin.oop

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class Cash
constructor(
    val amount: BigDecimal,
    val currency: Currency
) {
    constructor(
        amount: String,
        currency: Currency
    ) : this(BigDecimal(amount), currency)

    fun format(locale: Locale): String {
        val formatter = NumberFormat.getCurrencyInstance(locale)
        formatter.currency = currency
        return formatter.format(amount)
    }

    operator fun minus(other: Cash): Cash {
        require(currency == other.currency) {
            "Summand should be of the same currency"
        }
        return Cash(amount - other.amount, currency)
    }
}

fun main() {
    val a = Cash("10", Currency.getInstance("USD"))
    val b = Cash("20", Currency.getInstance("USD"))
    val c = b - a
    //c.amount = BigDecimal.TEN; // ERROR!
    println(c.amount)
    println(a)
    println(c.format(Locale.FRANCE))
}