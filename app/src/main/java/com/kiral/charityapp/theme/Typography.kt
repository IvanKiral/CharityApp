package com.kiral.charityapp.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.font
import androidx.compose.ui.text.font.fontFamily
import androidx.compose.ui.unit.sp
import com.kiral.charityapp.R

private val Helvetica = fontFamily(
    font(R.font.helvetica_neue),
    font(R.font.helvetica_neue_bold, FontWeight.W500),
)

val CharityTypography = Typography(
    h4 = TextStyle(
        fontFamily = Helvetica,
        fontWeight = FontWeight.W600,
        fontSize = 30.sp
    ),
    h5 = TextStyle(
        fontFamily = Helvetica,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),
    h6 = TextStyle(
        fontFamily = Helvetica,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Helvetica,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = Helvetica,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontFamily = Helvetica,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = Helvetica,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = Helvetica,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = Helvetica,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontFamily = Helvetica,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp
    )
)
