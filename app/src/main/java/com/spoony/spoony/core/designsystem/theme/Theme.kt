package com.spoony.spoony.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


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

private val LocalSpoonyColors = staticCompositionLocalOf<SpoonyColors> {
    error("No SpoonyColors provided")
}

private val LocalSpoonyTypography = staticCompositionLocalOf<SpoonyTypography> {
    error("No SpoonyTypography provided")
}

/*
* SpoonyTheme
*
* Color에 접근하고 싶을때 SpoonyTheme.colors.primary 이런식으로 접근하면 됩니다.
* Typo를 변경하고 싶다면 SpoonyTheme.typography.heading48B 이런식으로 접근하면 됩니다.
* */
object SpoonyAndroidTheme {
    val colors: SpoonyColors
        @Composable
        @ReadOnlyComposable
        get() = LocalSpoonyColors.current

    val typography: SpoonyTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalSpoonyTypography.current

}

@Composable
fun ProvideSpoonyColorsAndTypography(
    colors: SpoonyColors,
    typography: SpoonyTypography,
    content: @Composable () -> Unit
) {
    val provideColors = remember { colors.copy() }
    provideColors.update(colors)
    val provideTypography = remember { typography.copy() }.apply { update(typography) }
    CompositionLocalProvider(
        LocalSpoonyColors provides provideColors,
        LocalSpoonyTypography provides provideTypography,
        content = content
    )
}

@Composable
fun SpoonyAndroidTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    val colors = SpoonyLightColors()
    val typography = SpoonyTypography()
    ProvideSpoonyColorsAndTypography(colors, typography) {
        MaterialTheme(content = content)
    }
}