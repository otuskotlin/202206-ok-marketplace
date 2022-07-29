import kotlin.js.Date

actual fun currentDate(): DateString {
    return DateString(Date().toISOString())
}
