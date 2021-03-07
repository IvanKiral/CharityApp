package com.kiral.charityapp.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.kiral.charityapp.R
import com.kiral.charityapp.domain.model.CharityListItem
import com.kiral.charityapp.ui.theme.cardTextStyle
import com.kiral.charityapp.utils.Utils

@Composable
fun CharityItem(
    charity: CharityListItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 4.dp)
            .clickable(onClick = onClick)
    ) {
        charity.imgSrc.let { url ->
            val image = Utils.loadPicture(url = url, defaultImage = R.drawable.ic_loading_photo).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .clip(RoundedCornerShape(5.dp))
                )
            }
        }

        Text(
            text = charity.name,
            style = cardTextStyle,
            maxLines = 2,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}