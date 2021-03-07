package com.kiral.charityapp.ui.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiral.charityapp.ui.components.ProfileImageWithBorder

@Composable
fun ProfilePicture(
    name: String,
    imageBitmap: ImageBitmap?,
    imageSize: Dp,
    modifier: Modifier = Modifier,
    borderMargin: Dp = 14.dp,
    borderWidth: Dp = 1.dp,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileImageWithBorder(
            imageBitmap = imageBitmap,
            imageSize = imageSize,
            borderMargin = borderMargin,
            borderWidth = borderWidth
        )
        Text(
            text = name,
            style = MaterialTheme.typography.h4.copy(fontSize = 28.sp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 6.dp)
        )
    }
}