import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

actual fun currentDate(): DateString {
    return DateString(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
}
