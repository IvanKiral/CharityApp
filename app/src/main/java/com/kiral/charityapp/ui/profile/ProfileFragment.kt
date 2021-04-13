package com.kiral.charityapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.auth0.android.Auth0
import com.kiral.charityapp.R
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.ui.components.AlertDialogWithChoice
import com.kiral.charityapp.ui.components.BaseScreen
import com.kiral.charityapp.ui.components.ClickableIcon
import com.kiral.charityapp.ui.components.CountryDialog
import com.kiral.charityapp.ui.components.DonationField
import com.kiral.charityapp.ui.components.SingleChoicePicker
import com.kiral.charityapp.ui.dataStore
import com.kiral.charityapp.ui.home.CharitiesViewModel
import com.kiral.charityapp.ui.profile.components.Badges
import com.kiral.charityapp.ui.profile.components.Boxes
import com.kiral.charityapp.ui.profile.components.CategoriesDialog
import com.kiral.charityapp.ui.profile.components.OptionsMenu
import com.kiral.charityapp.ui.profile.components.ProfilePicture
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.ui.theme.DividerColor
import com.kiral.charityapp.utils.Auth
import com.kiral.charityapp.utils.Convert
import com.kiral.charityapp.utils.Utils.loadPicture
import com.kiral.charityapp.utils.makeGravatarLink
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    @Inject
    lateinit var account: Auth0

    private val viewModel: ProfileViewModel by viewModels()
    private val charitiesViewModel: CharitiesViewModel by activityViewModels()
    private val args: ProfileFragmentArgs by navArgs()

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

                ProfileScreen(
                    viewModel = viewModel,
                    donorId = args.id,
                    charitiesViewModel = charitiesViewModel,
                    onBackPressed = requireActivity()::onBackPressed,
                    navigateToCredits = {
                        findNavController()
                            .navigate(R.id.action_profileFragment_to_creditFragment)
                    },
                    navigateToBadges = { profileBadges ->
                        val action =
                            ProfileFragmentDirections.actionProfileFragmentToBadgesFragment(
                                profileBadges
                            )
                        findNavController().navigate(action)
                    },
                    logout = {
                        Auth.logout(
                            account,
                            requireContext(),
                            context.dataStore,
                        ) {
                            findNavController()
                                .navigate(R.id.action_profileFragment_to_welcomeFragment)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    donorId: Int,
    charitiesViewModel: CharitiesViewModel,
    navigateToBadges: (IntArray) -> Unit,
    navigateToCredits: () -> Unit,
    logout: () -> Unit,
    onBackPressed: () -> Unit
) {
    CharityTheme {
        BaseScreen(
            loading = viewModel.loading,
            error = viewModel.error,
            onRetryClicked = {
                viewModel.setProfile(donorId)
            }
        ) {
            viewModel.profile?.let { profile ->
                ProfileScreenBody(
                    profile = profile,
                    viewModel = viewModel,
                    charitiesViewModel = charitiesViewModel,
                    navigateToBadges = navigateToBadges,
                    navigateToCredits = navigateToCredits,
                    logout = logout,
                    onBackPressed = onBackPressed
                )
            }
        }
    }
}

@Composable
fun ProfileScreenBody(
    profile: Profile,
    viewModel: ProfileViewModel,
    charitiesViewModel: CharitiesViewModel,
    navigateToBadges: (IntArray) -> Unit,
    navigateToCredits: () -> Unit,
    logout: () -> Unit,
    onBackPressed: () -> Unit
){
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
        ) {
            val (profilePicture, back, divider, badges, donationField, boxRow, optionsMenu) = createRefs()
            val baseline = createGuidelineFromTop(300.dp)

            ClickableIcon(
                icon = ImageVector.vectorResource(id = R.drawable.ic_back),
                onIconClicked = onBackPressed,
                contentDescription = stringResource(id = R.string.back_icon_description),
                modifier = Modifier
                    .constrainAs(back) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start)
                    }
                    .offset(x = (-16).dp),
                size = 18.dp
            )
            ProfilePicture(
                name = profile.name,
                imageBitmap = profile.email.let { e ->
                    val img = loadPicture(
                        url = e.makeGravatarLink(),
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
                badges = viewModel.badges,
                navigateToBadges = { navigateToBadges(profile.badges.toIntArray()) },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(badges) {
                        top.linkTo(baseline, margin = 24.dp)
                    }
            )
            Boxes(
                credit = "${profile.credit.Convert()} €",
                donations = profile.donations.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(boxRow) {
                        top.linkTo(badges.bottom, margin = 24.dp)
                    },
                addButtonClick = { viewModel.onAddButtonClick() }
            )
            Box(
                modifier = Modifier
                    .constrainAs(donationField) {
                        top.linkTo(boxRow.bottom)
                    }
            ) {
                DonationField(
                    loading = viewModel.creditLoading,
                    shown = viewModel.credit,
                    buttonText = stringResource(R.string.profile_donationField_add),
                    onButtonClick = { value -> viewModel.addCredit(value) }
                )
            }
            OptionsMenu(
                regularDonationValue = profile.regularDonationValue,
                regularDonationFrequency = profile.regularDonationFrequency,
                region = profile.region,
                isSwitched = profile.regularDonationActive,
                categories = viewModel.categoryString,
                switchFunction = { value -> viewModel.setActive(value) },
                setDonationDialog = { value ->
                    viewModel.regularDonationDialog = value
                },
                setCountryDialog = { value -> viewModel.countryDialog = value },
                setCategoriesDialog = { value -> viewModel.categoriesDialog = value },
                logout = logout,
                navigateToCredits = navigateToCredits,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(optionsMenu) {
                        top.linkTo(donationField.bottom)
                    },
            )
            CountryDialog(
                countries = viewModel.countries.value,
                isShown = viewModel.countryDialog,
                setDialog = { value -> viewModel.countryDialog = value },
                setCountryText = { /*TODO*/ },
                setCountry = { value ->
                    viewModel.setRegion(value)
                    charitiesViewModel.getCharities(1)
                },
            )
            CategoriesDialog(
                shown = viewModel.categoriesDialog,
                setShowDialog = { viewModel.categoriesDialog = it },
                categoriesSelected = viewModel.selectedCategories,
                onConfirmButton = { viewModel.setCategories() }
            )
            AlertDialogWithChoice(
                title = stringResource(R.string.profile_alertDialog_title),
                shown = viewModel.regularDonationDialog,
                setShowDialog = { value -> viewModel.regularDonationDialog = value },
                onConfirmButton = { viewModel.setRegularPayment() }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SingleChoicePicker(
                        items = viewModel.moneyValues.map { v -> v.Convert() + " €" },
                        selectedItem = viewModel.selectedMoney,
                        setSelectedItem = { value -> viewModel.selectedMoney = value },
                        textAlignment = Alignment.End
                    )
                    SingleChoicePicker(
                        items = viewModel.frequencyValues,
                        selectedItem = viewModel.selectedFrequency,
                        setSelectedItem = { value ->
                            viewModel.selectedFrequency = value
                        },
                        textAlignment = Alignment.Start,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}
