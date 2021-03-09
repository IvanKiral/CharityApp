package com.kiral.charityapp.ui.detail

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.ui.components.BaseScreen
import com.kiral.charityapp.ui.components.DonationField
import com.kiral.charityapp.ui.components.DonationRow
import com.kiral.charityapp.ui.components.ExpandableText
import com.kiral.charityapp.ui.components.InformationBox
import com.kiral.charityapp.ui.detail.components.DetailHeader
import com.kiral.charityapp.ui.detail.components.DonationFailedAlertDialog
import com.kiral.charityapp.ui.detail.components.DonationSuccessAlertDialog
import com.kiral.charityapp.ui.detail.components.ProjectsList
import com.kiral.charityapp.ui.theme.BottomSheetShape
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.ui.theme.InformationBoxBlue
import com.kiral.charityapp.ui.theme.InformationBoxBlueBorder
import com.kiral.charityapp.ui.utils.buildInformationText
import com.kiral.charityapp.utils.Convert
import com.kiral.charityapp.utils.Utils
import com.kiral.charityapp.utils.checkCurrencyFormat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharityDetailFragment : Fragment() {
    private val args: CharityDetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

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
                val charity = viewModel.charity
                CharityDetailScreen(charity)
            }
        }
    }

    @ExperimentalMaterialApi
    @Composable
    fun CharityDetailScreen(charity: Charity?) {
        CharityTheme {
            BaseScreen(
                error = viewModel.error,
                loading = viewModel.loading,
                onRetryClicked = {
                    viewModel.getCharity(args.charityId, args.donorId)
                }
            ) {
                charity?.let { c ->
                    Box {
                        val configuration = LocalConfiguration.current
                        DetailHeader(
                            imgSrc = c.imgSrc,
                            donorDonated = c.donorDonated,
                            onBackPressed = requireActivity()::onBackPressed
                        )
                        Column(modifier = Modifier.fillMaxSize()) {
                            val bodyHeight = when (configuration.orientation) {
                                Configuration.ORIENTATION_LANDSCAPE -> 100.dp
                                else -> 204.dp
                            }
                            Spacer(Modifier.height(bodyHeight))
                            CharityDetailBody(
                                charity = c,
                                donorId = args.donorId,
                                viewModel = viewModel,
                                navController = findNavController(),
                                sharePhotoButtonClick = {
                                    Utils.sharePhoto(
                                        activity?.applicationContext!!,
                                        c.imgSrc
                                    )
                                },
                                shareLinkButtonClick = {
                                    val share = Intent.createChooser(Intent().apply {
                                        action = Intent.ACTION_SEND
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_TEXT, "https://cherrities.app")
                                    }, null)
                                    startActivity(share)
                                },
                            )
                        }
                    }
                }
            }

        }
    }
}


@Composable
fun CharityDetailBody(
    charity: Charity,
    donorId: Int,
    viewModel: DetailViewModel,
    navController: NavController,
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

            ExpandableText(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)) {
                        append("Our story\n")
                    }
                    append(charity.description + "\n\n")
                    withStyle(SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)) {
                        append("How donation helps\n")
                    }
                    append(charity.howDonationHelps + "\n\n")
                    withStyle(SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)) {
                        append("Why donate\n")
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
                price = "${charity.raised.Convert()}€",
                modifier = Modifier.padding(top = 16.dp),
                onButtonClick = {
                    viewModel.showDialog = !viewModel.showDialog
                }
            )
            DonationField(
                shown = viewModel.showDialog,
                loading = viewModel.donationLoading,
                modifier = Modifier.padding(top = 16.dp),
                onButtonClick = { donation ->
                    if (donation.checkCurrencyFormat())
                        viewModel.makeDonation(donorId, donation.toDouble())
                }
            )

            InformationBox(
                text = buildInformationText(
                    charity.peopleDonated,
                    charity.donorDonated,
                    "donated to this charity."
                ),
                backgroundColor = InformationBoxBlue,
                borderColor = InformationBoxBlueBorder,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                onClick = {
                    val action = CharityDetailFragmentDirections
                        .actionCharityDetailFragmentToDonorsFragment(charity.id)
                    navController.navigate(action)
                }
            )
            ProjectsList(
                projects = charity.projects,
                donorId = donorId,
                navController = navController,
                modifier = Modifier.padding(top = 16.dp)
            )

            DonationSuccessAlertDialog(
                shown = viewModel.showDonationSuccessDialog,
                setShowDialog = { value -> viewModel.showDonationSuccessDialog = value },
                sharePhotoButtonClick = sharePhotoButtonClick,
                shareLinkButtonClick = shareLinkButtonClick
            )

            DonationFailedAlertDialog(
                shown = viewModel.donationError != null,
                setShowDialog = { viewModel.donationError = null }
            )
        }
    }
}


