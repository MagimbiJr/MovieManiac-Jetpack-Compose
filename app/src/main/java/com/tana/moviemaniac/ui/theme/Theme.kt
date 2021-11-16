package com.tana.moviemaniac.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.colorspace.WhitePoint

private val DarkColorPalette = darkColors(
    //primary = Purple200,
    primary = PrimaryColor,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = NightDark,
    surface = OnDarkSurface
)

private val LightColorPalette = lightColors(
    //primary = Purple500,
    primary = PrimaryColor,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = WhiteSmoke,
    surface = OnLightSurface

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MovieAniTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
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