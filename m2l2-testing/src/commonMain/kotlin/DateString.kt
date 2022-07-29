data class DateString(
    val iso: String
)

//for ex 2022-04-16T07:50:06.696578
expect fun currentDate(): DateString
