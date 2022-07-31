fun getMessage(): String {
    return "Hello, my friend!"
}

fun getMessage(name: String): String {
    return if (name.uppercase() == name) {
        "HELLO $name!"
    } else {
        "Hello $name!"
    }
}

fun getMessage(vararg name: String): String {    
    return if (name.find { it.uppercase() == it } != null) {
        getMessage(name.joinToString(", ") {
            it.uppercase()
        })
    } else {
        getMessage(name.joinToString(", "))
    }
}
