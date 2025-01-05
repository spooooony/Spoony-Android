package com.spoony.spoony.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.spoony.spoony.R

val PretendardBold = FontFamily(Font(R.font.pretendard_bold, FontWeight.Bold))
val PretendardSemiBold = FontFamily(Font(R.font.pretendard_semibold, FontWeight.SemiBold))
val PretendardMedium = FontFamily(Font(R.font.pretendard_medium, FontWeight.Medium))

@Stable
class SpoonyTypography internal constructor(
    title1: TextStyle,
    title2b: TextStyle,
    title2sb: TextStyle,
    body1b: TextStyle,
    body1sb: TextStyle,
    body1m: TextStyle,
    body2b: TextStyle,
    body2sb: TextStyle,
    body2m: TextStyle,
    caption1b: TextStyle,
    caption1m: TextStyle,
    caption2b: TextStyle,
    caption2m: TextStyle,
) {

    var title1 by mutableStateOf(title1)
        private set
    var title2b by mutableStateOf(title2b)
        private set
    var title2sb by mutableStateOf(title2sb)
        private set
    var body1b by mutableStateOf(body1b)
        private set
    var body1sb by mutableStateOf(body1sb)
        private set
    var body1m by mutableStateOf(body1m)
        private set
    var body2b by mutableStateOf(body2b)
        private set
    var body2sb by mutableStateOf(body2sb)
        private set
    var body2m by mutableStateOf(body2m)
        private set
    var caption1b by mutableStateOf(caption1b)
        private set
    var caption1m by mutableStateOf(caption1m)
        private set
    var caption2b by mutableStateOf(caption2b)
        private set
    var caption2m by mutableStateOf(caption2m)
        private set

    fun copy(
        title1: TextStyle = this.title1,
        title2b: TextStyle = this.title2b,
        title2sb: TextStyle = this.title2sb,
        body1b: TextStyle = this.body1b,
        body1sb: TextStyle = this.body1sb,
        body1m: TextStyle = this.body1m,
        body2b: TextStyle = this.body2b,
        body2sb: TextStyle = this.body2sb,
        body2m: TextStyle = this.body2m,
        caption1b: TextStyle = this.caption1b,
        caption1m: TextStyle = this.caption1m,
        caption2b: TextStyle = this.caption2b,
        caption2m: TextStyle = this.caption2m,

    ): SpoonyTypography = SpoonyTypography(
        title1,
        title2b,
        title2sb,
        body1b,
        body1sb,
        body1m,
        body2b,
        body2sb,
        body2m,
        caption1b,
        caption1m,
        caption2b,
        caption2m,
    )

    fun update(other: SpoonyTypography) {
        title1 = other.title1
        title2b = other.title2b
        title2sb = other.title2sb
        body1b = other.body1b
        body1sb = other.body1sb
        body1m = other.body1m
        body2b = other.body2b
        body2sb = other.body2sb
        body2m = other.body2m
        caption1b = other.caption1b
        caption1m = other.caption1m
        caption2b = other.caption2b
        caption2m = other.caption2m
    }
}

@Composable
fun SpoonyTypography(): SpoonyTypography {
    return SpoonyTypography(
        title1 = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 20.sp,
            lineHeight = 1.45.em,
            letterSpacing = (-0.02).em,
        ),
        title2b = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 18.sp,
            lineHeight = 1.45.em,
            letterSpacing = (-0.02).em,
        ),
        title2sb = TextStyle(
            fontFamily = PretendardSemiBold,
            fontSize = 18.sp,
            lineHeight = 1.45.em,
            letterSpacing = (-0.02).em,
        ),
        body1b = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 16.sp,
            lineHeight = 1.45.em,
            letterSpacing = (-0.02).em,
        ),
        body1sb = TextStyle(
            fontFamily = PretendardSemiBold,
            fontSize = 16.sp,
            lineHeight = 1.45.em,
            letterSpacing = (-0.02).em,
        ),
        body1m = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 16.sp,
            lineHeight = 1.45.em,
            letterSpacing = (-0.02).em,
        ),
        body2b = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 14.sp,
            lineHeight = 1.45.em,
            letterSpacing = (-0.02).em,
        ),
        body2sb = TextStyle(
            fontFamily = PretendardSemiBold,
            fontSize = 14.sp,
            lineHeight = 1.45.em,
            letterSpacing = (-0.02).em,
        ),
        body2m = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 14.sp,
            lineHeight = 23.2.sp,
            letterSpacing = 0.32.sp,
        ),
        caption1b = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 12.sp,
            lineHeight = 1.45.em,
            letterSpacing = (-0.02).em,
        ),
        caption1m = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 12.sp,
            lineHeight = 1.45.em,
            letterSpacing = (-0.02).em,
        ),
        caption2b = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 10.sp,
            lineHeight = 1.45.em,
            letterSpacing = (-0.02).em,
        ),
        caption2m = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 10.sp,
            lineHeight = 1.45.em,
            letterSpacing = (-0.02).em,
        )
    )
}
