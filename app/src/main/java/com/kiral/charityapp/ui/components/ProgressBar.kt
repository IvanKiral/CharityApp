package com.kiral.charityapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kiral.charityapp.ui.theme.CherryRed

@Composable
fun ProgressBar(
    value: Double,
    maxValue: Double,
    modifier: Modifier = Modifier,
    height: Dp = 5.dp
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(Color(0xFFC4C4C4))
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth((value / maxValue).toFloat())
                .height(height)
                .background(CherryRed)

        )
    }
}