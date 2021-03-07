package com.kiral.charityapp.ui.badges.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.utils.Utils
import com.kiral.charityapp.utils.toGrayscale

@Composable
fun BadgesRow(
    badges: List<Badge>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        badges.forEach { badge ->
            BadgeCard(
                badge = badge
            )
        }
    }
}

@Composable
fun BadgeCard(
    badge: Badge,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(144.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val img = Utils.loadPicture(drawable = badge.iconId)
        img.value?.let {
            Image(
                bitmap = if (badge.active) it.toGrayscale()
                    .asImageBitmap() else it.asImageBitmap(),
                contentDescription = "badge icon",
                modifier = Modifier.size(128.dp)
            )
        }
        Text(
            text = badge.title,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}