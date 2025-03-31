package com.spoony.spoony.core.util.extension

import android.icu.text.BreakIterator
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale
import java.util.concurrent.atomic.AtomicReference

fun String?.toValidHexColor(): String =
    if (this != null && Regex("^[0-9A-Fa-f]{6}$").matches(this)) this else "FFFFFF"

fun String.formatToYearMonthDay(): String? {
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일", Locale.getDefault())
        val dateTime = LocalDateTime.parse(this, inputFormatter)
        dateTime.format(outputFormatter)
    } catch (e: DateTimeParseException) {
        null
    } catch (e: Exception) {
        null
    }
}

private val breakIteratorRef = AtomicReference<BreakIterator>()

fun String.countGraphemes(trim: Boolean = false): Int {
    val target = if (trim) this.trim() else this
    if (target.isEmpty()) return 0
    if (target.length == 1) return 1
    if (target.isLikelyAscii()) return target.length

    val iterator = breakIteratorRef.get() ?: BreakIterator.getCharacterInstance(Locale.ROOT).also {
        breakIteratorRef.set(it)
    }

    synchronized(iterator) {
        iterator.setText(target)
        var count = 0
        while (iterator.next() != BreakIterator.DONE) {
            count++
        }
        return count
    }
}

private fun String.isLikelyAscii(): Boolean {
    var i = 0
    val len = length
    while (i < len) {
        if (this[i].code > 0x7F) return false
        i++
    }
    return true
}
