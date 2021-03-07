package com.kiral.charityapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ProfileImageWithBorder(
    imageBitmap: ImageBitmap?,
    imageSize: Dp,
    modifier: Modifier = Modifier,
    borderMargin: Dp = 14.dp,
    borderWidth: Dp = 1.dp,
) {
    Box(
        modifier
            .size(imageSize + borderMargin + borderWidth)
            .border(
                width = borderWidth,
                color = Color.Black.copy(alpha = 0.10f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        imageBitmap?.let{
            Image(
                bitmap = imageBitmap,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(imageSize)
                    .clip(CircleShape)
            )
        }
    }
}