package com.spoony.spoony.core.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

// Main
val main0 = Color(0xFFFFEFEC)
val main100 = Color(0xFFFFCEC6)
val main200 = Color(0xFFFF9785)
val main300 = Color(0xFFFF755E)
val main400 = Color(0xFFFF5235)
val main500 = Color(0xFFE1482E)
val main600 = Color(0xFFBE3A24)
val main700 = Color(0xFF962D1C)
val main800 = Color(0xFF6E2317)
val main900 = Color(0xFF4E150B)

// Point
val orange400 = Color(0xFFFF7E35)
val pink400 = Color(0xFFFF7E84)
val green400 = Color(0xFF00CB92)
val blue400 = Color(0xFF6A92FF)
val purple400 = Color(0xFFAD75F9)
val orange100 = Color(0xFFFFE1D0)
val pink100 = Color(0xFFFFE4E5)
val green100 = Color(0xFFD3F4EB)
val blue100 = Color(0xFFDCE5FE)
val purple100 = Color(0xFFEEE3FD)

// State
val error400 = Color(0xFFFF364A)

// Gray scale
val white = Color(0xFFFFFFFF)
val black = Color(0xFF171719)
val gray0 = Color(0xFFF7F7F8)
val gray100 = Color(0xFFEAEBEC)
val gray200 = Color(0xFFDBDCDF)
val gray300 = Color(0xFFC2C4C8)
val gray400 = Color(0xFF989BA2)
val gray500 = Color(0xFF878A93)
val gray600 = Color(0xFF5A5C63)
val gray700 = Color(0xFF46474C)
val gray800 = Color(0xFF333438)
val gray900 = Color(0xFF292A2D)

@Stable
class SpoonyColors(
    main0: Color,
    main100: Color,
    main200: Color,
    main300: Color,
    main400: Color,
    main500: Color,
    main600: Color,
    main700: Color,
    main800: Color,
    main900: Color,
    orange400: Color,
    pink400: Color,
    green400: Color,
    blue400: Color,
    purple400: Color,
    orange100: Color,
    pink100: Color,
    green100: Color,
    blue100: Color,
    purple100: Color,
    error400: Color,
    white: Color,
    black: Color,
    gray0: Color,
    gray100: Color,
    gray200: Color,
    gray300: Color,
    gray400: Color,
    gray500: Color,
    gray600: Color,
    gray700: Color,
    gray800: Color,
    gray900: Color,
    isLight: Boolean
) {
    var main0 by mutableStateOf(main0)
        private set
    var main100 by mutableStateOf(main100)
        private set
    var main200 by mutableStateOf(main200)
        private set
    var main300 by mutableStateOf(main300)
        private set
    var main400 by mutableStateOf(main400)
        private set
    var main500 by mutableStateOf(main500)
        private set
    var main600 by mutableStateOf(main600)
        private set
    var main700 by mutableStateOf(main700)
        private set
    var main800 by mutableStateOf(main800)
        private set
    var main900 by mutableStateOf(main900)
        private set
    var orange400 by mutableStateOf(orange400)
        private set
    var pink400 by mutableStateOf(pink400)
        private set
    var green400 by mutableStateOf(green400)
        private set
    var blue400 by mutableStateOf(blue400)
        private set
    var purple400 by mutableStateOf(purple400)
        private set
    var orange100 by mutableStateOf(orange100)
        private set
    var pink100 by mutableStateOf(pink100)
        private set
    var green100 by mutableStateOf(green100)
        private set
    var blue100 by mutableStateOf(blue100)
        private set
    var purple100 by mutableStateOf(purple100)
        private set
    var error400 by mutableStateOf(error400)
        private set
    var white by mutableStateOf(white)
        private set
    var black by mutableStateOf(black)
        private set
    var gray0 by mutableStateOf(gray0)
        private set
    var gray100 by mutableStateOf(gray100)
        private set
    var gray200 by mutableStateOf(gray200)
        private set
    var gray300 by mutableStateOf(gray300)
        private set
    var gray400 by mutableStateOf(gray400)
        private set
    var gray500 by mutableStateOf(gray500)
        private set
    var gray600 by mutableStateOf(gray600)
        private set
    var gray700 by mutableStateOf(gray700)
        private set
    var gray800 by mutableStateOf(gray800)
        private set
    var gray900 by mutableStateOf(gray900)
        private set
    var isLight by mutableStateOf(isLight)

    fun copy(): SpoonyColors = SpoonyColors(
        main0,
        main100,
        main200,
        main300,
        main400,
        main500,
        main600,
        main700,
        main800,
        main900,
        orange400,
        pink400,
        green400,
        blue400,
        purple400,
        orange100,
        pink100,
        green100,
        blue100,
        purple100,
        error400,
        white,
        black,
        gray0,
        gray100,
        gray200,
        gray300,
        gray400,
        gray500,
        gray600,
        gray700,
        gray800,
        gray900,
        isLight
    )

    fun update(other: SpoonyColors) {
        main0 = other.main0
        main100 = other.main100
        main200 = other.main200
        main300 = other.main300
        main400 = other.main400
        main500 = other.main500
        main600 = other.main600
        main700 = other.main700
        main800 = other.main800
        main900 = other.main900
        orange400 = other.orange400
        pink400 = other.pink400
        green400 = other.green400
        blue400 = other.blue400
        purple400 = other.purple400
        orange100 = other.orange100
        pink100 = other.pink100
        green100 = other.green100
        blue100 = other.blue100
        purple100 = other.purple100
        error400 = other.error400
        white = other.white
        black = other.black
        gray0 = other.gray0
        gray100 = other.gray100
        gray200 = other.gray200
        gray300 = other.gray300
        gray400 = other.gray400
        gray500 = other.gray500
        gray600 = other.gray600
        gray700 = other.gray700
        gray800 = other.gray800
        gray900 = other.gray900
        isLight = other.isLight
    }
}

fun SpoonyLightColors(
    Main0: Color = main0,
    Main100: Color = main100,
    Main200: Color = main200,
    Main300: Color = main300,
    Main400: Color = main400,
    Main500: Color = main500,
    Main600: Color = main600,
    Main700: Color = main700,
    Main800: Color = main800,
    Main900: Color = main900,
    Orange400: Color = orange400,
    Pink400: Color = pink400,
    Green400: Color = green400,
    Blue400: Color = blue400,
    Purple400: Color = purple400,
    Orange100: Color = orange100,
    Pink100: Color = pink100,
    Green100: Color = green100,
    Blue100: Color = blue100,
    Purple100: Color = purple100,
    Error400: Color = error400,
    White: Color = white,
    Black: Color = black,
    Gray0: Color = gray0,
    Gray100: Color = gray100,
    Gray200: Color = gray200,
    Gray300: Color = gray300,
    Gray400: Color = gray400,
    Gray500: Color = gray500,
    Gray600: Color = gray600,
    Gray700: Color = gray700,
    Gray800: Color = gray800,
    Gray900: Color = gray900,
) = SpoonyColors(
    Main0,
    Main100,
    Main200,
    Main300,
    Main400,
    Main500,
    Main600,
    Main700,
    Main800,
    Main900,
    Orange400,
    Pink400,
    Green400,
    Blue400,
    Purple400,
    Orange100,
    Pink100,
    Green100,
    Blue100,
    Purple100,
    Error400,
    White,
    Black,
    Gray0,
    Gray100,
    Gray200,
    Gray300,
    Gray400,
    Gray500,
    Gray600,
    Gray700,
    Gray800,
    Gray900,
    isLight = true
)

@Preview(showBackground = true)
@Composable
fun SpoonyMainColorsPreview() {
    SpoonyAndroidTheme {
        Column {
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.main0,
                modifier = Modifier.background(
                    color = SpoonyAndroidTheme.colors.black
                )
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.main100,
                modifier = Modifier.background(
                    color = SpoonyAndroidTheme.colors.black
                )
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.main200
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.main300
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.main400
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.main500
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.main600
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.main700
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.main800
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.main900
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SpoonyPointColorsPreview() {
    SpoonyAndroidTheme {
        Column {
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.orange400
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.orange100,
                modifier = Modifier.background(
                    color = SpoonyAndroidTheme.colors.black
                )
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.pink400
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.pink100,
                modifier = Modifier.background(
                    color = SpoonyAndroidTheme.colors.black
                )
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.green400
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.green100,
                modifier = Modifier.background(
                    color = SpoonyAndroidTheme.colors.black
                )
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.blue400
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.blue100,
                modifier = Modifier.background(
                    color = SpoonyAndroidTheme.colors.black
                )
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.purple400
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.purple100,
                modifier = Modifier.background(
                    color = SpoonyAndroidTheme.colors.black
                )
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.error400
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SpoonyStateColorsPreview() {
    SpoonyAndroidTheme {
        Column {
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.error400,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SpoonyGrayScaleColorsPreview() {
    SpoonyAndroidTheme {
        Column {
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.white,
                modifier = Modifier.background(
                    color = SpoonyAndroidTheme.colors.black
                )
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.black
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.gray0,
                modifier = Modifier.background(
                    color = SpoonyAndroidTheme.colors.black
                )
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.gray100,
                modifier = Modifier.background(
                    color = SpoonyAndroidTheme.colors.black
                )
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.gray200
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.gray300
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.gray400
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.gray500
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.gray600
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.gray700
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.gray800
            )
            Text(
                "SpoonyAndroidTheme",
                style = SpoonyAndroidTheme.typography.title1,
                color = SpoonyAndroidTheme.colors.gray900
            )
        }
    }
}