package com.kiral.charityapp.ui.donors.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kiral.charityapp.R
import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.ui.components.ProfileImageWithBorder
import com.kiral.charityapp.ui.theme.TextOptionSubtitle

@Composable
fun ProfileCard(
    imageBitmap: ImageBitmap?,
    name: String,
    badges: List<Badge>,
    donated: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(136.dp)
            .graphicsLayer(),
        elevation = 12.dp,
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImageWithBorder(
                imageBitmap = imageBitmap,
                borderMargin = 6.dp,
                imageSize = 72.dp
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth(0.7f)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.body2
                )
                Row(modifier = Modifier.padding(top = 4.dp)) {
                    badges.take(3).forEachIndexed { i, b ->
                        Image(
                            imageVector = ImageVector.vectorResource(id = b.iconId),
                            contentDescription = stringResource(R.string.donors_icon_description),
                            contentScale = ContentScale.FillHeight,
                            modifier = if (i != 0) Modifier
                                .height(18.dp)
                                .padding(start = 8.dp)
                            else Modifier.height(18.dp)
                        )
                    }
                    if(badges.size > 3) {
                        Text(
                            text = "+ ${(badges.size - 3)}",
                            style = MaterialTheme.typography.body1.copy(color = TextOptionSubtitle),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_heart),
                    contentDescription = stringResource(R.string.donors_heartIcon_description),
                    modifier = Modifier
                        .size(24.dp)
                        .padding(top = 8.dp),
                )
                Text(
                    text = "$donated€",
                    style = MaterialTheme.typography.body1.copy(color = TextOptionSubtitle),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}