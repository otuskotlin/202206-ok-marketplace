actual class Logger {
    actual fun log(message: String) {
        println("JVM log: $message")
    }
}