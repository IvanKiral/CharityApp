package com.kiral.charityapp.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollableController
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientDensity
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import com.kiral.charityapp.R
import com.kiral.charityapp.login.ClickableLogo
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
                    ScrollableColumn() {
                        ConstraintLayout(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 32.dp),
                        ) {
                            val (profilePicture, back, divider, badges, boxrow, optionsMenu) = createRefs()
                            val baseline = createGuidelineFromTop(300.dp)

                            ClickableLogo(
                                icon = vectorResource(id = R.drawable.ic_back),
                                onIconClicked = { /*TODO*/ },
                                modifier = Modifier.constrainAs(back){
                                    top.linkTo(parent.top, margin = 16.dp)
                                    start.linkTo(parent.start)
                                }
                                    .offset(x = -16.dp)
                            )
                            ProfilePicture(
                                name = "Rachel Green",
                                imageBitmap = imageResource(id = R.drawable.rachel),
                                imageSize = 128.dp,
                                modifier = Modifier.constrainAs(profilePicture) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(baseline)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                            )
                            Divider(
                                thickness = 1.dp,
                                color = Color(0xFFEEF2F8),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .constrainAs(divider) {
                                        bottom.linkTo(baseline, margin = 8.dp)
                                    }
                            )
                            Badges(
                                badges = data,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .constrainAs(badges) {
                                        top.linkTo(baseline, margin = 24.dp)
                                    }
                            )
                            BoxRow(
                                credit = "27.5€",
                                donations = "87",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .constrainAs(boxrow) {
                                        top.linkTo(badges.bottom, margin = 24.dp)
                                    }
                            )
                            OptionsMenu(modifier = Modifier
                                .fillMaxWidth()
                                .constrainAs(optionsMenu) {
                                    top.linkTo(boxrow.bottom)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ProfilePicture(
        name: String,
        imageBitmap: ImageBitmap,
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

    @Composable
    fun Badges(
        badges: List<BadgeData>,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your Badges",
                style = MaterialTheme.typography.h6.copy(color = Color(0xFF94A0AF))
            )

            BadgeRow(
                badges = badges,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            ClickableText(
                text = AnnotatedString("Show badges"),
                style = MaterialTheme.typography.body1.copy(color = Color(0xFFB7C1CC)),
                modifier = Modifier.padding(top = 24.dp),
                onClick = { /*TODO*/ }
            )
        }
    }

    @Composable
    fun BoxRow(
        credit: String,
        donations: String,
        modifier: Modifier = Modifier
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
                            color = Color(0xFF4B5564)
                        )
                    )
                    Text(
                        text = "Donations",
                        style = MaterialTheme.typography.button.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF5A6470)
                        )
                    )
                }
            }
            BoxWithAdd(
                boxWeight = 136.dp,
                boxHeight = 96.dp,
                modifier = Modifier.padding(vertical = 16.dp),
                showAddButton = true,
                gradientStartColor = Color(0xFF0965EE),
                gradientEndColor = Color(0xFF0050C9),
                addButtonClick = {
                    Toast.makeText(requireContext(), "click", Toast.LENGTH_SHORT).show()
                }
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = credit,
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
    badges: List<BadgeData>,
    modifier: Modifier = Modifier
) {
    Box {
        Box(
            modifier
                .height(20.dp)
                .fillMaxWidth()
                .offset(y = 20.dp)
                .shadow(36.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier

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
            contentDescription = "",
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
                .shadow(16.dp, shape = RoundedCornerShape(12.dp))
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
                        color = Color(0xFF0050C9)
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
