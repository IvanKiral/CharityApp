package com.kiral.charityapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.ui.components.AlertDialogWithChoice
import com.kiral.charityapp.ui.components.BaseScreen
import com.kiral.charityapp.ui.components.DonationRow
import com.kiral.charityapp.ui.components.ExpandableText
import com.kiral.charityapp.ui.components.InformationAlertDialog
import com.kiral.charityapp.ui.components.InformationBox
import com.kiral.charityapp.ui.components.SingleChoicePicker
import com.kiral.charityapp.ui.detail.components.DetailHeader
import com.kiral.charityapp.ui.detail.components.ProjectsList
import com.kiral.charityapp.ui.theme.BottomSheetShape
import com.kiral.charityapp.ui.theme.ButtonBlue
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.ui.theme.InformationBoxBlue
import com.kiral.charityapp.ui.theme.InformationBoxBlueBorder
import com.kiral.charityapp.ui.utils.buildInformationText
import com.kiral.charityapp.utils.Convert
import com.kiral.charityapp.utils.Utils
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
                Column {
                    charity?.let { c ->
                        DetailHeader(
                            imgSrc = c.imgSrc,
                            donorDonated = c.donorDonated,
                            onBackPressed = requireActivity()::onBackPressed
                        )
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
                            modifier = Modifier.offset(y = (-20).dp)
                        )
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
            .fillMaxSize()
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
                text = "Domov mladeze Krasna Horka",
                style = MaterialTheme.typography.body1,
                color = Color.Black.copy(0.5f)
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
                price = "${charity.raised.toDouble().Convert()}€",
                modifier = Modifier.padding(top = 24.dp),
                onButtonClick = {
                    viewModel.showDialog = true
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
            if (viewModel.showDialog) {
                AlertDialogWithChoice(
                    setShowDialog = { value -> viewModel.showDialog = value },
                    title = "Select value to donate",
                    onConfirmButton = { viewModel.makeDonation(donorId) }
                ) {
                    SingleChoicePicker(
                        items = viewModel.values.map { v -> v.Convert() + " €" },
                        selectedItem = viewModel.selectedValue,
                        setSelectedItem = { value -> viewModel.selectedValue = value },
                        textAlignment = Alignment.CenterHorizontally
                    )
                }
            }
            if (viewModel.showDonationSuccessDialog) {
                InformationAlertDialog(
                    title = "Thank you for your contribution!",
                    buttonText = "Okay",
                    setShowDialog = { value -> viewModel.showDonationSuccessDialog = value }
                ) {
                    Column {
                        Text(
                            "You did great today! Wanna share about your contribution?",
                            textAlign = TextAlign.Justify
                        )
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White,
                            ),
                            onClick = sharePhotoButtonClick
                        ) {
                            Text("Share photo...", color = ButtonBlue)
                        }
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White,
                            ),
                            onClick = shareLinkButtonClick
                        ) {
                            Text("Share link...", color = ButtonBlue)
                        }
                    }
                }
            }
        }
    }
}
