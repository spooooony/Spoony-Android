package com.spoony.spoony.core.util.extension

import com.spoony.spoony.core.designsystem.model.BirthDate
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import timber.log.Timber

private val MICROSECOND_REGEX = Regex("\\.\\d+")

fun String?.toValidHexColor(): String =
    if (this != null && Regex("^[0-9A-Fa-f]{6}$").matches(this)) this else "FFFFFF"

fun String.formatToYearMonthDay(): String {
    return try {
        val cleanedDate = this.replace(MICROSECOND_REGEX, "")
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일", Locale.getDefault())
        val dateTime = LocalDateTime.parse(cleanedDate, inputFormatter)
        dateTime.format(outputFormatter)
    } catch (e: Exception) {
        Timber.e(e)
        ""
    }
}

fun String.toRelativeTimeOrDate(): String {
    return try {
        val cleanedDate = this.replace(MICROSECOND_REGEX, "")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val dateTime = LocalDateTime.parse(cleanedDate, formatter)

        val now = LocalDateTime.now()
        val duration = Duration.between(dateTime, now)

        when {
            duration.toMinutes() < 1 -> "방금 전"
            duration.toHours() < 1 -> "약 ${duration.toMinutes()}분 전"
            duration.toHours() < 24 -> "약 ${duration.toHours()}시간 전"
            else -> this.formatToYearMonthDay()
        }
    } catch (e: Exception) {
        Timber.e(e)
        ""
    }
}

fun String?.toBirthDate(): BirthDate? {
    val dateList = this?.split("-") ?: listOf()
    return if (dateList.size == 3) {
        BirthDate(dateList[0], dateList[1], dateList[2])
    } else {
        null
    }
}

fun formatBirthDate(
    isBirthSelected: Boolean,
    year: String?,
    month: String?,
    day: String?
): String? {
    return if (isBirthSelected && year != null && month != null && day != null) {
        "$year-${month.padStart(2, '0')}-${day.padStart(2, '0')}"
    } else {
        null
    }
}
