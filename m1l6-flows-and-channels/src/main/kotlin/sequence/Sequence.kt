package sequence

enum class Color {
    YELLOW,
    GREEN,
    BLUE,
    RED,
    VIOLET,

}

enum class Shape {
    SQUARE,
    CIRCLE,
    TRIANGLE,
    RHOMBUS

}

data class Figure(
    val color: Color,
    val shape: Shape
)

fun processAsList(
    words: List<String>,
    wordLength: Int, take: Int,
    printOperation: Boolean
) {
    println("Processing list")
    var counter = 0

    val lengthsList = words
        .filter {
            counter++
            if (printOperation) println("filter: $it")

            it.length > wordLength
        }
        .map {
            counter++
            if (printOperation) println("length: ${it.length}")

            it.length
        }
        .take(take)

    println("Lengths of first $take words longer than $wordLength chars:")
    println(lengthsList)

    println("List counter: $counter")
    println()
}

fun processAsSeq(
    words: List<String>,
    wordLength: Int,
    take: Int,
    printOperation: Boolean
) {
    println("Processing sequence")
    var counter = 0

    //convert the List to a Sequence
    val wordsSequence = words.asSequence()

    val lengthsSequence = wordsSequence
        .filter {
            counter++
            if (printOperation) println("filter: $it")

            it.length > wordLength
        }
        .map {
            counter++
            if (printOperation) println("length: ${it.length}")

            it.length
        }
        .take(take)
        .toList()

    println("Lengths of first $take words longer than $wordLength chars")
    println(lengthsSequence)

    println("Sequence counter: $counter")
    println()
}