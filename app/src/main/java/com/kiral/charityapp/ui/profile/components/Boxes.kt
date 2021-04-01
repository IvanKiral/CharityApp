package com.kiral.charityapp.ui.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.theme.BoxGradientEnd
import com.kiral.charityapp.ui.theme.BoxGradientStart
import com.kiral.charityapp.ui.theme.TextBoxBlackSubTitle
import com.kiral.charityapp.ui.theme.TextBoxBlackTitle

@Composable
fun Boxes(
    credit: String,
    donations: String,
    modifier: Modifier = Modifier,
    addButtonClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BoxWithAdd(
            boxWeight = 136.dp,
            boxHeight = 96.dp,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = donations,
                    style = MaterialTheme.typography.button.copy(
                        fontSize = 24.sp,
                        color = TextBoxBlackTitle
                    )
                )
                Text(
                    text = stringResource(R.string.profile_boxDonations),
                    style = MaterialTheme.typography.button.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = TextBoxBlackSubTitle
                    )
                )
            }
        }
        BoxWithAdd(
            boxWeight = 136.dp,
            boxHeight = 96.dp,
            modifier = Modifier.padding(vertical = 16.dp),
            showAddButton = true,
            gradientStartColor = BoxGradientStart,
            gradientEndColor = BoxGradientEnd,
            addButtonClick = addButtonClick
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = credit,
                    style = MaterialTheme.typography.button.copy(fontSize = 24.sp)
                )
                Text(
                    text = stringResource(R.string.profile_boxCredit),
                    style = MaterialTheme.typography.button.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White.copy(alpha = 0.5f)
                    )
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
                    .offset(y = (-16).dp)
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
                        color = MaterialTheme.colors.primary
                    )
                )
            }
        }
    }
}