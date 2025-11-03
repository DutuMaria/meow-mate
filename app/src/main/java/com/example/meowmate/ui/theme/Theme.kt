package com.example.meowmate.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColors: ColorScheme = lightColorScheme(
    primary = PurplePrimary,
    onPrimary = PurpleOnPrimary,
    primaryContainer = PurpleContainer,
    onPrimaryContainer = PurpleOnContainer,

    secondary = MintSecondary,
    onSecondary = MintOnSecondary,
    secondaryContainer = MintContainer,
    onSecondaryContainer = MintOnContainer,

    tertiary = OrangeTertiary,
    onTertiary = OrangeOnTertiary,
    tertiaryContainer = OrangeContainer,
    onTertiaryContainer = OrangeOnContainer,

    background = LightBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    outline = LightOutline
)

private val DarkColors: ColorScheme = darkColorScheme(
    primary = PurplePrimaryDarkMode,
    onPrimary = PurpleOnPrimaryDarkMode,
    primaryContainer = PurpleContainerDarkMode,
    onPrimaryContainer = PurpleOnContainerDarkMode,

    secondary = MintSecondaryDarkMode,
    onSecondary = MintOnSecondaryDarkMode,
    secondaryContainer = MintContainerDarkMode,
    onSecondaryContainer = MintOnContainerDarkMode,

    tertiary = OrangeTertiaryDarkMode,
    onTertiary = OrangeOnTertiaryDarkMode,
    tertiaryContainer = OrangeContainerDarkMode,
    onTertiaryContainer = OrangeOnContainerDarkMode,

    background = DarkBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    outline = DarkOutline
)

@Composable
fun MeowMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
