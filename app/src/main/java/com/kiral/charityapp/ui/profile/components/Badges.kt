package com.kiral.charityapp.ui.profile.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kiral.charityapp.R
import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.ui.theme.TextBadgesTitle
import com.kiral.charityapp.ui.theme.TextShowBadges
import kotlin.math.abs

@Composable
fun Badges(
    badges: List<Badge>,
    navigateToBadges: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.profile_yourBadges),
            style = MaterialTheme.typography.h6.copy(color = TextBadgesTitle)
        )
        BadgeRow(
            badges = badges,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        ClickableText(
            text = AnnotatedString(stringResource(R.string.profile_showBadges)),
            style = MaterialTheme.typography.body1.copy(color = TextShowBadges),
            modifier = Modifier.padding(top = 24.dp),
            onClick = { navigateToBadges() }
        )
    }
}


@Composable
fun BadgeRow(
    badges: List<Badge>,
    modifier: Modifier = Modifier
) {
    Box {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier

        ) {
            val configuration = LocalConfiguration.current
            val boxDifference  = when(configuration.orientation){
                Configuration.ORIENTATION_LANDSCAPE -> 20.dp
                else  -> 8.dp
            }
            val imagePadding  = when(configuration.orientation){
                Configuration.ORIENTATION_LANDSCAPE -> 16.dp
                else  -> 6.dp
            }
            val lstMiddle = listOf(3,1,0,2,4).size / 2
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val size = (maxWidth  - boxDifference.times(8)) / 5
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier

                ) {
                    listOf(3,1,0,2,4).forEachIndexed { index, i ->
                        val badgeSize = size + (boxDifference.times(lstMiddle - abs(index - lstMiddle)))
                        Badge(
                            icon = if (i < badges.size) ImageVector.vectorResource(id = badges[i].iconId)
                                else null,
                            iconSize = (badgeSize.minus(imagePadding.times(4)))
                                    + ((lstMiddle - abs(index - lstMiddle)) * 5).dp,
                            boxSize = badgeSize
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun Badge(
    icon: ImageVector?,
    iconSize: Dp,
    boxSize: Dp,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 12.dp
) {
    Surface(
        modifier = modifier
            .size(boxSize),
        shape = RoundedCornerShape(cornerRadius),
        elevation = 8.dp
    ) {
        Box(modifier = Modifier
            .size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            icon?.let {
                Image(
                    imageVector = it,
                    contentDescription = stringResource(R.string.profile_badge_icon_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(iconSize)
                )
            }
        }
    }
}