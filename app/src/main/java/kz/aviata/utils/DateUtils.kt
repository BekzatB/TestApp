package kz.aviata.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


object DateUtils {
    const val ZONE_ID = "Asia/Almaty"
    val ddMMyyyy = "dd/MM/yyyy"


}
fun LocalDate.formatWithPattern(pattern: String): String =
    this.format(DateTimeFormatter.ofPattern(pattern, Locale.UK))

