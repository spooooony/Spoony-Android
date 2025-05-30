package com.spoony.spoony.core.util.extension

import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val hyphenFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

fun LocalDate.toHyphenDate(): String =
    this.format(hyphenFormatter)
