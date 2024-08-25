package com.manishsputnikcorporation.movies.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val lightColors =
    lightColors(
        primary = Red700,
        primaryVariant = Red900,
        onPrimary = Color.White,
        secondary = Red700,
        secondaryVariant = Red900,
        onSecondary = Color.White,
        error = Red800)

private val darkColors =
    darkColors(
        primary = Red300, primaryVariant = Red700, onPrimary = Color.Black, secondary = Red300, onSecondary = Color.Black, error = Red200)

/** A [MaterialTheme] for MoviesTheme */
@Composable
fun MoviesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (darkTheme) darkColors else lightColors, typography = MoviesTypography, content = content)
}
