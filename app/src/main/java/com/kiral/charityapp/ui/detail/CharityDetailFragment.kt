package com.kiral.charityapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kiral.charityapp.R
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.ui.components.BaseScreen
import com.kiral.charityapp.ui.components.DonationField
import com.kiral.charityapp.ui.components.ExpandableText
import com.kiral.charityapp.ui.components.InformationBox
import com.kiral.charityapp.ui.detail.components.DetailScreen
import com.kiral.charityapp.ui.detail.components.DonationFailedAlertDialog
import com.kiral.charityapp.ui.detail.components.DonationRow
import com.kiral.charityapp.ui.detail.components.DonationSuccessAlertDialog
import com.kiral.charityapp.ui.detail.components.ProjectsList
import com.kiral.charityapp.ui.theme.BottomSheetShape
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.ui.theme.InformationBoxRed
import com.kiral.charityapp.ui.theme.InformationBoxRedBorder
import com.kiral.charityapp.ui.utils.buildInformationText
import com.kiral.charityapp.utils.Utils
import com.kiral.charityapp.utils.convert
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharityDetailFragment : Fragment() {
    private val args: CharityDetailFragmentArgs by navArgs()
    private val viewModel: CharityDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCharity(id = args.charityId, args.donorId)
    }

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CharityDetailScreen(
                    charityId = args.charityId,
                    donorId = args.donorId,
                    viewModel = viewModel,
                    onBackPressed = requireActivity()::onBackPressed,
                    navigateToProject = { projectId ->
                        val action = CharityDetailFragmentDirections
                            .actionCharityDetailFragmentToProjectDetailFragment(
                                projectId = projectId,
                                args.donorId
                            )
                        findNavController().navigate(action)

                    },
                    navigateToDonors = {
                        val action = CharityDetailFragmentDirections
                            .actionCharityDetailFragmentToDonorsFragment(
                                charityId = args.charityId,
                                projectId = -1,
                                userId = args.donorId
                            )
                        findNavController().navigate(action)
                    },

                    )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun CharityDetailScreen(
    charityId: Int,
    donorId: Int,
    viewModel: CharityDetailViewModel,
    onBackPressed: () -> Unit,
    navigateToProject: (Int) -> Unit,
    navigateToDonors: () -> Unit,
) {
    CharityTheme {
        BaseScreen(
            error = viewModel.error,
            loading = viewModel.loading,
            onRetryClicked = {
                viewModel.getCharity(charityId, donorId)
            }
        ) {
            viewModel.charity?.let { c ->
                DetailScreen(
                    imgSrc = c.imgSrc,
                    donorDonated = c.donorDonated,
                    onClosePressed = onBackPressed
                ) {
                    val ctx = LocalContext.current
                    CharityDetailBody(
                        charity = c,
                        donorId = donorId,
                        viewModel = viewModel,
                        navigateToDonors = navigateToDonors,
                        navigateToProject = navigateToProject,
                        sharePhotoButtonClick = {
                            viewModel.addBadge(donorId)
                            Utils.sharePhoto(
                                ctx,
                                c.imgSrc
                            )
                        },
                        shareLinkButtonClick = {
                            viewModel.addBadge(donorId)
                            Utils.shareLink(ctx)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun CharityDetailBody(
    charity: Charity,
    donorId: Int,
    viewModel: CharityDetailViewModel,
    navigateToDonors: () -> Unit,
    navigateToProject: (Int) -> Unit,
    sharePhotoButtonClick: () -> Unit,
    shareLinkButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier
            .fillMaxHeight()
            .clip(BottomSheetShape),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(BottomSheetShape)
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = charity.name,
                style = MaterialTheme.typography.h5
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = charity.address,
                style = MaterialTheme.typography.body1
            )

            // need to store strings in variable, if not compiler error is thrown
            val charityStoryTitle = stringResource(R.string.detail_charity_charityStory)
            val howCharityHelps = stringResource(R.string.detail_charity_howCharityHelps)
            val whyDonate = stringResource(R.string.detail_charity_whyDonate)

            ExpandableText(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)) {
                        append(charityStoryTitle + "\n")
                    }
                    append(charity.description + "\n\n")
                    withStyle(SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)) {
                        append(howCharityHelps + "\n")
                    }
                    append(charity.howDonationHelps + "\n\n")
                    withStyle(SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)) {
                        append(whyDonate + "\n")
                    }
                    append(charity.whyToDonate)
                },
                modifier = Modifier.padding(top = 16.dp)
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            )

            DonationRow(
                price = "${charity.raised.convert()}€",
                modifier = Modifier.padding(top = 16.dp),
                onButtonClick = {
                    viewModel.onExtraDonateButtonPressed()
                }
            )
            DonationField(
                shown = viewModel.showDonate,
                loading = viewModel.donationLoading,
                buttonText = stringResource(R.string.detail_donate),
                modifier = Modifier.padding(top = 16.dp),
                onButtonClick = { donation -> viewModel.onDonateButtonPressed(donorId, donation) }
            )

            InformationBox(
                text = buildInformationText(
                    charity.peopleDonated,
                    charity.donorDonated,
                    stringResource(R.string.detail_peopleDonated_charity_postfix)
                ),
                backgroundColor = InformationBoxRed,
                borderColor = InformationBoxRedBorder,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                onClick = navigateToDonors
            )
            ProjectsList(
                projects = charity.projects,
                navigateToProject = navigateToProject,
                modifier = Modifier.padding(top = 16.dp)
            )

            DonationSuccessAlertDialog(
                shown = viewModel.showDonationSuccessDialog,
                setShowDialog = { value -> viewModel.setDonationSuccessDialog(value) },
                sharePhotoButtonClick = sharePhotoButtonClick,
                shareLinkButtonClick = shareLinkButtonClick
            )

            DonationFailedAlertDialog(
                description = viewModel.donationError,
                shown = viewModel.shouldShowDonationFailedDialog(),
                setShowDialog = { viewModel.donationError = null }
            )
        }
    }
}



