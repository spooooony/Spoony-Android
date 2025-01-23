package com.spoony.spoony.core.util.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String?.toValidHexColor(): String =
    if (this != null && Regex("^[0-9A-Fa-f]{6}$").matches(this)) this else "FFFFFF"

fun String?.formatToYearMonthDay(): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일", Locale.getDefault())
    val dateTime = LocalDateTime.parse(this, inputFormatter)
    return dateTime.format(outputFormatter)
}
