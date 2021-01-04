package com.kiral.charityapp.theme

import androidx.compose.material.Typography
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.font
import androidx.compose.ui.text.font.fontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.kiral.charityapp.R

val Helvetica = fontFamily(
    font(R.font.helvetica_neue),
    font(R.font.helvetica_neue_bold, FontWeight.W500),
)

val labelTextStyle = TextStyle(
    color = TextBlack,
    fontFamily = Helvetica,
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp
)

val CharityTypography = Typography(
    h4 = TextStyle(
        fontFamily = Helvetica,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    h5 = TextStyle(
        fontFamily = Helvetica,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontFamily = Helvetica,
        fontWeight = FontWeight.Bold,
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
        color = TextBlack,
        fontFamily = Helvetica,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        color = TextBlack,
        fontFamily = Helvetica,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    button = TextStyle(
        color = Color.White,
        fontFamily = Helvetica,
        fontWeight = FontWeight.W700,
        fontSize = 18.sp
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
