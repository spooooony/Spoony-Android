package com.spoony.spoony.core.util.extension

import android.graphics.Color.parseColor
import androidx.compose.ui.graphics.Color

fun Color.Companion.hexToColor(hex: String): Color {
    val colorInt = parseColor("#$hex")
    return Color(colorInt)
}
