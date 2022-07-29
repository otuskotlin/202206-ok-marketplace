package continuation

import kotlinx.coroutines.delay

suspend fun main() {
    coroutine()
}

suspend fun coroutine() {
    val text = getText()
    printText(text)
}

suspend fun getText(): String {
    delay(2000)
    return "hello, otus"
}

fun printText(text: String) {
    println(text)
}