package com.kiral.charityapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.ui.profile.BadgeData
import com.kiral.charityapp.ui.theme.*

@Composable
fun ProfileImageWithBorder(
    imageBitmap: ImageBitmap,
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


@Composable
fun BadgeRow(
    badges: List<Badge>,
    modifier: Modifier = Modifier
) {
    Box {
        Box(
            modifier
                .height(8.dp)
                .fillMaxWidth()
                .offset(y = 36.dp)
                .shadow(20.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier

        ) {
            listOf(3, 1, 0, 2, 4).forEachIndexed{ index, i ->
                Badge(
                    icon = if(i < badges.size) vectorResource(id = badges[i].iconId) else null,
                    iconSize = (22 + (2 - Math.abs(index - 2)) * 5).dp,
                    boxSize = (52 + (2 - Math.abs(index - 2)) * 8).dp
                )
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
    Box(
        modifier = modifier
            .size(boxSize)
            .clip(RoundedCornerShape(cornerRadius))
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        icon?.let {
            Image(
                imageVector = it,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(iconSize)
            )
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
                    checked = true,
                    onCheckedChange = switchFunction,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    colors = SwitchDefaults.colors(checkedThumbColor = ButtonBlue)
                )
            }
        }
    }
}