package com.kiral.charityapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale

@Composable
fun ClickableIcon(
    icon: ImageVector,
    onIconClicked: () -> Unit,
    modifier: Modifier = Modifier,
    alpha: Float = 1f
) {
    Image(
        imageVector = icon,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        alpha = alpha,
        modifier = modifier.clickable(onClick = onIconClicked)
    )
}