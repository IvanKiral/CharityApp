package com.kiral.charityapp.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightThemeColors = lightColors(
    primary = ButtonBlue,
    onPrimary = Color.Black,
    secondary = Color.White,
    secondaryVariant = ButtonBlue,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    surface = Color.White,
)

@Composable
fun CharityTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    progressBarIsDisplayed: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = LightThemeColors,
        typography = CharityTypography,
        content = content
    )
}




