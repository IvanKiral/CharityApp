package com.kiral.charityapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ClickableIcon(
    icon: ImageVector,
    onIconClicked: () -> Unit,
    modifier: Modifier = Modifier,
    alpha: Float = 1f,
    size: Dp
) {
    Box(
        modifier = modifier.clickable(onClick = onIconClicked)
    ) {
        Image(
            imageVector = icon,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            alpha = alpha,
            modifier = Modifier.padding(6.dp).size(size)
        )
    }
}