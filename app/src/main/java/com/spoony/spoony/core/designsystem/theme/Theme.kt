package com.spoony.spoony.core.designsystem.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = main400,
    background = white
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
    val colorScheme = LightColorScheme
    val typography = SpoonyTypography()

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = true
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightNavigationBars = true
        }
    }

    ProvideSpoonyColorsAndTypography(colors, typography) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}
