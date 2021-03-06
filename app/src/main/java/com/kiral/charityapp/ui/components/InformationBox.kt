package com.kiral.charityapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InformationBox(
    text: AnnotatedString,
    backgroundColor: Color,
    borderColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(58.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(backgroundColor)
            .border(width = 1.dp, color = borderColor)
            .clickable(onClick = onClick)
    ) {
        Row() {
            Text(
                text = text,
                style = MaterialTheme.typography.body1.copy(fontSize = 13.sp)
            )
        }
    }
}