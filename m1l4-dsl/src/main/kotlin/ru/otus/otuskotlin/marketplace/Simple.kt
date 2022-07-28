package ru.otus.otuskotlin.marketplace

fun sout(block: () -> Any?) {
    val result = block()
    println(result)
}

class MyContext {
    fun time() = System.currentTimeMillis()
//    fun time(): Long {
//        return System.currentTimeMillis()
//    }
}

fun soutWithTimestamp(block: MyContext.() -> Any?) {
    val context = MyContext()
    val result = block(context)
    println(result)
}

infix fun String.time(value: String): String {
    return "$this:$value"
}

fun main() {
    val pair = Pair("key", "value")

    val pairNew = "key" to "value"

    val myTimeOld = "12".time("30")
    val myTime = "12" time "30"
}
