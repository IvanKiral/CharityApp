package com.kiral.charityapp.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientDensity
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.kiral.charityapp.R
import com.kiral.charityapp.theme.ButtonBlue
import com.kiral.charityapp.theme.CharityTheme
import com.kiral.charityapp.utils.drawColoredShadow
import java.lang.Math.abs


data class BadgeData(
    val icon: Int,
)

class ProfileFragment : Fragment() {
    val data = listOf<BadgeData>(
        BadgeData(R.drawable.ic_dog),
        BadgeData(R.drawable.ic_dog),
        BadgeData(R.drawable.ic_dog),
        BadgeData(R.drawable.ic_dog),
        BadgeData(R.drawable.ic_dog)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CharityTheme {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 32.dp)
                    ) {
                        ProfileImageWithBorder(
                            imageBitmap = imageResource(id = R.drawable.rachel),
                            imageSize = 128.dp
                        )
                        BadgeRow(
                            badges = data,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        )
                        BoxWithAdd(
                            boxWeight = 136.dp,
                            boxHeight = 96.dp,
                            modifier = Modifier.padding(vertical = 16.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "27.5€",
                                    style = MaterialTheme.typography.button.copy(fontSize = 24.sp)
                                )
                                Text(
                                    text = "Credit",
                                    style = MaterialTheme.typography.button.copy(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.White.copy(alpha = 0.5f)
                                    )
                                )
                            }
                        }
                        OptionsMenu(modifier = Modifier.fillMaxWidth())

                    }
                }
            }
        }
    }

    @Composable
    fun OptionsMenu(
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier
        ) {
            Option(
                title = "Regular donations",
                description = "1€/day",
                hasSwitch = true,
                modifier = Modifier.fillMaxWidth()
            )
            Option(
                title = "Logout",
                description = "Logout from the system",
                hasSwitch = false,
                modifier = Modifier.fillMaxWidth()
            )
            Divider(
                thickness = 1.dp,
                color = Color(0xFFEEF2F8),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

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
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape)

        )
    }
}

@Composable
fun BadgeRow(
    badges: List<BadgeData>,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .shadow(64.dp, shape = RoundedCornerShape(12.dp), clip = false)
    ) {
        for (i in 0..4) {
            Badge(
                icon = vectorResource(id = badges[i].icon),
                iconSize = (22 + (2 - abs(i - 2)) * 5).dp,
                boxSize = (52 + (2 - abs(i - 2)) * 8).dp
            )
        }
    }
}

@Composable
fun Badge(
    icon: ImageVector,
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
        Image(
            imageVector = icon,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(iconSize)
        )
    }
}

@Composable
fun BoxWithAdd(
    boxWeight: Dp,
    boxHeight: Dp,
    modifier: Modifier = Modifier,
    addButtonClick: () -> Unit = {},
    boxContent: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(boxWeight, boxHeight)
                .shadow(36.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF0965EE), Color(0xFF0050C9)),
                        0F,
                        boxHeight.value
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            boxContent()
        }
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
                    color = Color(0xFF0050C9)
                )
            )
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
            color = Color(0xFFEEF2F8),
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
                    style = MaterialTheme.typography.h6.copy(color = Color(0xFF4B5564))
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.body1.copy(color = Color(0xFF969FAD)),
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
