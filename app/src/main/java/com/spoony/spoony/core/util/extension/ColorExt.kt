package com.spoony.spoony.core.util.extension

import androidx.compose.ui.graphics.Color

fun Color.Companion.hexToColor(hex: String): Color {
    val colorInt = android.graphics.Color.parseColor("#$hex")
    return Color(colorInt)
}
