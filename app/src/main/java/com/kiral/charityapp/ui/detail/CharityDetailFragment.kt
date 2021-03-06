package com.kiral.charityapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kiral.charityapp.R
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.domain.model.CharityProject
import com.kiral.charityapp.ui.components.AlertDialogWithChoice
import com.kiral.charityapp.ui.components.DonationBox
import com.kiral.charityapp.ui.components.DonationRow
import com.kiral.charityapp.ui.components.ErrorScreen
import com.kiral.charityapp.ui.components.ExpandableText
import com.kiral.charityapp.ui.components.InformationAlertDialog
import com.kiral.charityapp.ui.components.InformationBox
import com.kiral.charityapp.ui.components.LoadingScreen
import com.kiral.charityapp.ui.components.SingleChoicePicker
import com.kiral.charityapp.ui.theme.BottomSheetShape
import com.kiral.charityapp.ui.theme.ButtonBlue
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.ui.theme.InformationBoxBlue
import com.kiral.charityapp.ui.theme.InformationBoxBlueBorder
import com.kiral.charityapp.ui.utils.buildInformationText
import com.kiral.charityapp.utils.Convert
import com.kiral.charityapp.utils.loadPicture
import com.kiral.charityapp.utils.sharePhoto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharityDetailFragment : Fragment() {
    val args: CharityDetailFragmentArgs by navArgs()
    val viewModel: DetailViewModel by viewModels()

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
    fun CharityDetailScreen(charity: androidx.compose.runtime.State<Charity?>) {
        CharityTheme {
            if (viewModel.error.value == null) {
                if (viewModel.loading.value) {
                    LoadingScreen()
                } else {
                    Column {
                        charity.value?.let { c ->
                            CharityDetailHeader(c.imgSrc, c.donorDonated)
                            CharityDetailBody(c, modifier = Modifier.offset(y = -20.dp))
                        }
                    }
                }
            } else {
                ErrorScreen(text = viewModel.error.value!!)
            }
        }
    }

    @Composable
    fun CharityDetailHeader(
        imgSrc: String,
        donorDonated: Double,
    ) {
        Box() {
            imgSrc.let { src ->
                val image = loadPicture(url = src, defaultImage = R.drawable.children)
                image.value?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(230.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 24.dp, end = 16.dp)
            ) {
                if (donorDonated > 0) {
                    DonationBox(
                        text = "You donated ${donorDonated.Convert()}  €",
                        backgroundColor = Color.Black.copy(alpha = 0.5f),
                    )
                }
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_close),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable(onClick = { requireActivity().onBackPressed() })
                )
            }
        }
    }

    @Composable
    fun CharityDetailBody(
        charity: Charity,
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
                    text = "Domov Mladeze Krasna Horka",
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
                        viewModel.setShowDialog(true)
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
                            .actionCharityDetailFragmentToDonorsFragment(args.charityId)
                        findNavController().navigate(action)
                    }
                )
                ProjectsColumn(
                    projects = charity.projects,
                    modifier = Modifier.padding(top = 16.dp)
                )
                if (viewModel.showDialog.value) {
                    AlertDialogWithChoice(
                        setShowDialog = { viewModel.setShowDialog(it) },
                        title = "Select value to donate",
                        onConfirmButton = { viewModel.makeDonation(args.donorId) }
                    ) {
                        SingleChoicePicker(
                            items = viewModel.values.map { v -> v.Convert() + " €" },
                            selectedItem = viewModel.selectedValue.value,
                            setSelectedItem = { viewModel.setSelectedValue(it) },
                            textAlignment = Alignment.CenterHorizontally
                        )
                    }
                }
                if (viewModel.showDonationSuccessDialog.value) {
                    InformationAlertDialog(
                        title = "Thank you for your contribution!",
                        buttonText = "Okay",
                        setShowDialog = { viewModel.setDonationSuccessDialog(it) }
                    ) {
                        Column() {
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
                                onClick = {
                                    sharePhoto(
                                        activity?.applicationContext!!,
                                        charity.imgSrc
                                    )
                                }) {
                                Text("Share photo via", color = ButtonBlue)
                            }
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.White,
                                ),
                                onClick = {
                                    val share = Intent.createChooser(Intent().apply {
                                        action = Intent.ACTION_SEND
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_TEXT, "https://cherrities.app")
                                    }, null)
                                    startActivity(share)
                                }) {
                                Text("Share link via", color = ButtonBlue)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ProjectsColumn(
        projects: List<CharityProject>,
        modifier: Modifier = Modifier
    ) {
        Column(modifier = modifier) {
            Text(
                text = "Projects",
                style = MaterialTheme.typography.body1
            )
            projects.forEach { project ->
                ClickableText(
                    text = AnnotatedString(project.name),
                    style = MaterialTheme.typography.h5,
                    onClick = {
                        val action = CharityDetailFragmentDirections
                            .actionCharityDetailFragmentToProjectDetailFragment(
                                project.id,
                                args.donorId
                            )
                        findNavController().navigate(action)
                    },
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }
    }
}
