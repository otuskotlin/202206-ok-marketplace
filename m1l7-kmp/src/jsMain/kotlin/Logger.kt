actual class Logger {
    actual fun log(message: String) {
        console.log("JS log: $message")
    }
}