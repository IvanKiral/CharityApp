package com.kiral.charityapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


val BottomSheetShape = RoundedCornerShape(
    topStart = CornerSize(20.dp),
    topEnd = CornerSize(20.dp),
    bottomStart = ZeroCornerSize,
    bottomEnd = ZeroCornerSize
)

private val LightThemeColors = lightColors(
    primary = CherryRed,
    onPrimary = Color.Black,
    secondary = Color.White,
    secondaryVariant = CherryRed,
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




