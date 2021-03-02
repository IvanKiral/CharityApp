package com.kiral.charityapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.ui.theme.ButtonBlue
import com.kiral.charityapp.ui.theme.DividerColor
import com.kiral.charityapp.ui.theme.TextBlue
import com.kiral.charityapp.ui.theme.TextOptionSubtitle
import com.kiral.charityapp.ui.theme.TextOptionTitle

@Composable
fun ProfileImageWithBorder(
    imageBitmap: ImageBitmap?,
    imageSize: Dp,
    modifier: Modifier = Modifier,
    borderMargin: Dp = 14.dp,
    borderWidth: Dp = 1.dp,
) {
    Box(
        modifier
            .size(imageSize + borderMargin + borderWidth)
            .border(
                width = borderWidth,
                color = Color.Black.copy(alpha = 0.10f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        imageBitmap?.let{
            Image(
                bitmap = imageBitmap,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(imageSize)
                    .clip(CircleShape)
            )
        }
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
                        val badgeSize = size + (boxDifference.times(lstMiddle - Math.abs(index - lstMiddle)))
                        Badge(
                            icon = if (i < badges.size) ImageVector.vectorResource(id = badges[i].iconId) else null,
                            iconSize = (badgeSize.minus(imagePadding.times(4))) + ((lstMiddle - Math.abs(index - lstMiddle)) * 5).dp,
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
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(iconSize)
                )
            }
        }
    }
}

@Composable
fun BoxWithAdd(
    boxWeight: Dp,
    boxHeight: Dp,
    modifier: Modifier = Modifier,
    showAddButton: Boolean = false,
    gradientStartColor: Color = Color.White,
    gradientEndColor: Color = Color.White,
    addButtonClick: () -> Unit = {},
    boxContent: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(boxWeight, boxHeight)
                .shadow(12.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(gradientStartColor, gradientEndColor),
                        0F,
                        boxHeight.value
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            boxContent()
        }
        if (showAddButton) {
            Box(
                modifier = Modifier
                    .offset(y = -16.dp)
                    .align(Alignment.CenterHorizontally)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(color = Color.White)
                    .clickable(onClick = addButtonClick),
                contentAlignment = Alignment.Center
            )
            {
                Text(
                    text = "+",
                    style = MaterialTheme.typography.h5.copy(
                        fontSize = 20.sp,
                        color = TextBlue
                    )
                )
            }
        }
    }
}

@Composable
fun Option(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    isSwitched: Boolean = false,
    hasSwitch: Boolean = false,
    onClick: () -> Unit = {},
    switchFunction: (Boolean) -> Unit = {}
) {
    Column(
        modifier = modifier
            .height(80.dp)
            .clickable(onClick = onClick)
    ) {
        Divider(
            thickness = 1.dp,
            color = DividerColor,
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Column() {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h6.copy(color = TextOptionTitle)
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.body1.copy(color = TextOptionSubtitle),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
            if (hasSwitch) {
                Switch(
                    checked = isSwitched,
                    onCheckedChange = switchFunction,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    colors = SwitchDefaults.colors(checkedThumbColor = ButtonBlue)
                )
            }
        }
    }
}