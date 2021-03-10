package com.kiral.charityapp.ui.detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.components.DonationBox
import com.kiral.charityapp.utils.Utils

@Composable
fun DetailHeader(
    imgSrc: String,
    donorDonated: Double,
    onBackPressed: () -> Unit
) {
    Box() {
        imgSrc.let { src ->
            val image = Utils.loadPicture(url = src, defaultImage = R.drawable.children)
            image.value?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(224.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 24.dp, end = 16.dp)
        ) {
            if (donorDonated > 0) {
                DonationBox(
                    text = stringResource(
                        id = R.string.detail_userDonated
                        //donorDonated.Convert()
                    ),
                    backgroundColor = Color.Black.copy(alpha = 0.5f),
                )
            }
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_close),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable(onClick = onBackPressed)
            )
        }
    }
}