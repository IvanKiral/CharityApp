package com.kiral.charityapp.components

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
    modifier: Modifier = Modifier
) {
    Image(
        imageVector = icon,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier.clickable(onClick = onIconClicked)
    )
}