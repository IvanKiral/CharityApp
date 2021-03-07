package com.kiral.charityapp.ui.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.kiral.charityapp.ui.theme.ProfileIconBorder

@ExperimentalFoundationApi
@Composable
fun IconRoundCorner(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {}
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterEnd)
                .size(56.dp)
                .border(width = 1.dp, color = ProfileIconBorder, shape = CircleShape)
                .clip(shape = CircleShape)
                .combinedClickable(onClick = onClick, onLongClick = onLongClick)
        ) {
            Image(
                imageVector = imageVector,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}