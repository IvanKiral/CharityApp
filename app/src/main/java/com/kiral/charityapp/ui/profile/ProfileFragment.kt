package com.kiral.charityapp.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.auth0.android.Auth0
import com.kiral.charityapp.R
import com.kiral.charityapp.domain.enums.DonationFrequency
import com.kiral.charityapp.domain.fakeBadges
import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.ui.components.AlertDialogWithChoice
import com.kiral.charityapp.ui.components.BadgeRow
import com.kiral.charityapp.ui.components.BoxWithAdd
import com.kiral.charityapp.ui.components.ClickableIcon
import com.kiral.charityapp.ui.components.CountryDialog
import com.kiral.charityapp.ui.components.Option
import com.kiral.charityapp.ui.components.ProfileImageWithBorder
import com.kiral.charityapp.ui.components.SingleChoicePicker
import com.kiral.charityapp.ui.theme.BoxGradientEnd
import com.kiral.charityapp.ui.theme.BoxGradientStart
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.ui.theme.DividerColor
import com.kiral.charityapp.ui.theme.TextBadgesTitle
import com.kiral.charityapp.ui.theme.TextBoxBlackSubTitle
import com.kiral.charityapp.ui.theme.TextBoxBlackTitle
import com.kiral.charityapp.ui.theme.TextShowBadges
import com.kiral.charityapp.utils.Auth
import com.kiral.charityapp.utils.Convert
import com.kiral.charityapp.utils.loadPicture
import com.kiral.charityapp.utils.makeGravatrLink
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    @Inject
    lateinit var dataStore: DataStore<Preferences>

    @Inject
    lateinit var account: Auth0

    private val viewModel: ProfileViewModel by viewModels()
    private val args: ProfileFragmentArgs by navArgs()
    private lateinit var profile: State<Profile?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setProfile(args.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CharityTheme {
                    if (viewModel.profile.value != null) {
                        ProfileScreen(viewModel.profile)
                    }
                }
            }
        }
    }

    @Composable
    fun ProfileScreen(profile: State<Profile?>) {
        profile.value?.let { p ->
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp),
                ) {
                    val (profilePicture, back, divider, badges, boxrow, optionsMenu) = createRefs()
                    val baseline = createGuidelineFromTop(300.dp)

                    ClickableIcon(
                        icon = ImageVector.vectorResource(id = R.drawable.ic_back),
                        onIconClicked = { /*TODO*/ },
                        modifier = Modifier
                            .constrainAs(back) {
                                top.linkTo(parent.top, margin = 16.dp)
                                start.linkTo(parent.start)
                            }
                            .offset(x = -16.dp)
                    )
                    ProfilePicture(
                        name = p.name,
                        imageBitmap = p.email.let { e ->
                            val img = loadPicture(
                                url = e.makeGravatrLink(),
                                defaultImage = R.drawable.ic_loading_photo
                            )
                            img.value?.asImageBitmap()
                        },
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
                        badges = fakeBadges,
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(badges) {
                                top.linkTo(baseline, margin = 24.dp)
                            }
                    )
                    BoxRow(
                        credit = "${ p.credit.Convert() } €",
                        donations = p.donations.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(boxrow) {
                                top.linkTo(badges.bottom, margin = 24.dp)
                            },
                        context = activity?.applicationContext!!
                    )
                    OptionsMenu(
                        setDonationDialog = { viewModel.setRegularDonationDialog(it) },
                        regularDonationValue = p.regularDonationValue,
                        regularDonationFrequency = p.regularDonationFrequency,
                        region = p.region,
                        isSwitched = p.regularDonationActive,
                        switchFunction = { b ->
                            viewModel.setActive(b)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(optionsMenu) {
                                top.linkTo(boxrow.bottom)
                            },
                    )
                    CountryDialog(
                        countries = viewModel.countries.value,
                        isShown = viewModel.countryDialog.value,
                        setDialog = { viewModel.setCountryDialog(it) },
                        setCountryText = { /*TODO*/ },
                        setCountry = { viewModel.setRegion(it) },
                    )
                    if (viewModel.regularDonationDialog.value) {
                        AlertDialogWithChoice(
                            title = "Choose value and frequency of regular donations",
                            setShowDialog = { viewModel.setRegularDonationDialog(it) },
                            onConfirmButton = {
                                viewModel.setRegularPayment(args.id)
                            }
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                SingleChoicePicker(
                                    items = viewModel.moneyValues.map { v -> v.Convert() + " €" },
                                    selectedItem = viewModel.selectedMoney.value,
                                    setSelectedItem = { viewModel.setSelectedMoney(it) },
                                    textAlignment = Alignment.End
                                )
                                SingleChoicePicker(
                                    items = viewModel.frequencyValues,
                                    selectedItem = viewModel.selectedFrequency.value,
                                    setSelectedItem = { viewModel.setSelectedFrequency(it) },
                                    textAlignment = Alignment.Start,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ProfilePicture(
        name: String,
        imageBitmap: ImageBitmap?,
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
        badges: List<Badge>,
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
        modifier: Modifier = Modifier,
        context: Context
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
                    Toast.makeText(context, "click", Toast.LENGTH_SHORT).show()
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
        modifier: Modifier = Modifier,
        regularDonationValue: Double,
        regularDonationFrequency: Int,
        region: String,
        isSwitched: Boolean,
        switchFunction: (Boolean) -> Unit,
        setDonationDialog: (Boolean) -> Unit
    ) {
        Column(
            modifier = modifier
        ) {

            Option(
                title = stringResource(R.string.ProfileFragment_RegularDonations),
                description = "${regularDonationValue.Convert()} €/${DonationFrequency.values()[regularDonationFrequency]}",
                hasSwitch = true,
                isSwitched = isSwitched,
                switchFunction = switchFunction,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    setDonationDialog(true)
                }
            )
            Option(
                title = "Select charity types",
                description = "",
                hasSwitch = false,
                modifier = Modifier.fillMaxWidth(),
                onClick = {}
            )
            Option(
                title = "Select country",
                description = Locale("", region).displayCountry,
                hasSwitch = false,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.setCountryDialog(true)
                }
            )
            Option(
                title = stringResource(R.string.ProfileFragment_Logout),
                description = stringResource(R.string.ProfileFragment_LogoutDescription),
                hasSwitch = false,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    Auth.logout(
                        account,
                        requireContext(),
                        dataStore,
                    ) {
                        findNavController().navigate(R.id.action_profileFragment_to_welcomeFragment)
                    }
                }
            )
            Divider(
                thickness = 1.dp,
                color = DividerColor,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}



