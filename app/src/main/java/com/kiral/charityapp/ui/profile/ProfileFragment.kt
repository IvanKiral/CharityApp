package com.kiral.charityapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.components.*
import com.kiral.charityapp.ui.theme.*

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
                ProfileScreen()
            }
        }
    }

    @Composable
    fun ProfileScreen() {
        CharityTheme {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp),
                ) {
                    val (profilePicture, back, divider, badges, boxrow, optionsMenu) = createRefs()
                    val baseline = createGuidelineFromTop(300.dp)

                    ClickableIcon(
                        icon = vectorResource(id = R.drawable.ic_back),
                        onIconClicked = { /*TODO*/ },
                        modifier = Modifier
                            .constrainAs(back) {
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
                        color = DividerColor,
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
                text = stringResource(R.string.ProfileFragment_YourBadges),
                style = MaterialTheme.typography.h6.copy(color = TextBadgesTitle)
            )
            BadgeRow(
                badges = badges,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            ClickableText(
                text = AnnotatedString(stringResource(R.string.ProfileFragment_ShowBadges)),
                style = MaterialTheme.typography.body1.copy(color = TextShowBadges),
                modifier = Modifier.padding(top = 24.dp),
                onClick = { findNavController().navigate(R.id.action_profileFragment_to_badgesFragment) }
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
                            color = TextBoxBlackTitle
                        )
                    )
                    Text(
                        text = stringResource(R.string.ProfileFragment_BoxDonations),
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
                        text = stringResource(R.string.ProfileFragment_BoxCredit),
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
                title = stringResource(R.string.ProfileFragment_RegularDonations),
                description = "1€/day",
                hasSwitch = true,
                modifier = Modifier.fillMaxWidth()
            )
            Option(
                title = stringResource(R.string.ProfileFragment_Logout),
                description = stringResource(R.string.ProfileFragment_LogoutDescription),
                hasSwitch = false,
                modifier = Modifier.fillMaxWidth()
            )
            Divider(
                thickness = 1.dp,
                color = DividerColor,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}



