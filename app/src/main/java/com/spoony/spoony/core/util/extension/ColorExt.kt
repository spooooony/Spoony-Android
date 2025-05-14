package com.spoony.spoony.core.util.extension

import android.graphics.Color.parseColor
import androidx.compose.ui.graphics.Color

fun Color.Companion.hexToColor(hex: String): Color {
    return try {
        if (hex.isEmpty()) {
            Unspecified
        } else {
            val colorInt = parseColor("#$hex")
            Color(colorInt)
        }
    } catch (e: Exception) {
        Unspecified
    }
}
