package com.spoony.spoony.core.designsystem.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class SpoonyTypography(
    val title1: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 23.2.sp,
        letterSpacing = 0.32.sp,
    ),
    val title2b: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 23.2.sp,
        letterSpacing = 0.32.sp,
    ),
    val title2sb: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 23.2.sp,
        letterSpacing = 0.32.sp,
    ),
    val body1b: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 23.2.sp,
        letterSpacing = 0.32.sp,
    ),
    val body1sb: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 23.2.sp,
        letterSpacing = 0.32.sp,
    ),
    val body1m: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 23.2.sp,
        letterSpacing = 0.32.sp,
    ),
    val body2b: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 23.2.sp,
        letterSpacing = 0.32.sp,
    ),
    val body2sb: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 23.2.sp,
        letterSpacing = 0.32.sp,
    ),
    val body2m: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 23.2.sp,
        letterSpacing = 0.32.sp,
    ),
    val caption1b: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 23.2.sp,
        letterSpacing = 0.32.sp,
    ),
    val caption1m: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 23.2.sp,
        letterSpacing = 0.32.sp,
    ),
    val caption2b: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        lineHeight = 23.2.sp,
        letterSpacing = 0.32.sp,
    ),
    val caption2m: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 23.2.sp,
        letterSpacing = 0.32.sp,
    ),
) {
    fun copy(): SpoonyTypography = this

    fun update(other: SpoonyTypography) {}
}

fun SpoonyTypography(): SpoonyTypography = SpoonyTypography()
