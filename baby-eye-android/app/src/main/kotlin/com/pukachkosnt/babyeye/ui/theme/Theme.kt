package com.pukachkosnt.babyeye.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary        = Color(0xFFffb782),
    primaryVariant = Color(0xFFc7ca95),
    onPrimary      = Color(0xFF4f2500),
    secondary      = Color(0xFF755945),
    onSecondary    = Color(0xFF422b1a),
    surface        = Color(0xFF201a17),
    onSurface      = Color(0xFFece0da),
    error          = Color(0xFFffb4ab),
    onError        = Color(0xFF690005),
)

private val LightColorPalette = lightColors(
    primary        = Color(0xFF934b00),
    primaryVariant = Color(0xFF5e6135),
    onPrimary      = White,
    secondary      = Color(0xFFe4bfa7),
    onSecondary    = White,
    surface        = White,
    onSurface      = Color(0xFF201a17),
    error          = Color(0xFFba1a1a),
    onError        = White
)

@Composable
fun BabyEyeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}