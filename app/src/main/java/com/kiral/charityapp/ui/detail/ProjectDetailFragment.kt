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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.kiral.charityapp.R
import com.kiral.charityapp.domain.model.Project
import com.kiral.charityapp.ui.components.AlertDialogWithChoice
import com.kiral.charityapp.ui.components.DonationBox
import com.kiral.charityapp.ui.components.ExpandableText
import com.kiral.charityapp.ui.components.InformationAlertDialog
import com.kiral.charityapp.ui.components.InformationBox
import com.kiral.charityapp.ui.components.ProgressBar
import com.kiral.charityapp.ui.components.SingleChoicePicker
import com.kiral.charityapp.ui.theme.BottomSheetShape
import com.kiral.charityapp.ui.theme.ButtonBlue
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.ui.theme.InformationBoxBlue
import com.kiral.charityapp.ui.theme.InformationBoxBlueBorder
import com.kiral.charityapp.ui.theme.TextDonationGray
import com.kiral.charityapp.ui.utils.buildInformationText
import com.kiral.charityapp.utils.Convert
import com.kiral.charityapp.utils.loadPicture
import com.kiral.charityapp.utils.sharePhoto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectDetailFragment : Fragment() {

    val args: ProjectDetailFragmentArgs by navArgs()
    val viewModel: ProjectDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getProject(args.projectId, args.donorId)
    }

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CharityDetailScreen()
            }
        }
    }

    @ExperimentalMaterialApi
    @Composable
    fun CharityDetailScreen() {
        CharityTheme() {
            Column {
                val project = viewModel.project
                project.value?.let { p ->
                    CharityDetailHeader(p.charityImage, p.donorDonated)
                    CharityDetailBody(p, modifier = Modifier.offset(y = -20.dp))
                }

            }
        }
    }

    @Composable
    fun CharityDetailHeader(
        charityImage: String,
        donorDonated: Double
    ) {
        Box() {
            charityImage.let { src ->
                val image = loadPicture(url = src, defaultImage = R.drawable.children)
                image.value?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .preferredHeight(230.dp)
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
                    imageVector = vectorResource(id = R.drawable.ic_close),
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
        project: Project,
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
                    text = project.name,
                    style = MaterialTheme.typography.h5
                )

                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = project.charityName,
                    style = MaterialTheme.typography.body1,
                    color = Color.Black.copy(0.5f)
                )

                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = project.charityAdress,
                    style = MaterialTheme.typography.body1
                )

                ExpandableText(
                    text = AnnotatedString(project.description),
                    modifier = Modifier.padding(top = 16.dp)
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )
                CharityRaisedColumn(
                    actualSum = project.actualSum,
                    goalSum = project.goalSum,
                    onButtonClick = { viewModel.setShowDialog(true) },
                    modifier = Modifier.padding(top = 16.dp)
                )
                InformationBox(
                    text = buildInformationText(
                        project.peopleDonated,
                        project.donorDonated,
                        "donated to this project."
                    ),
                    backgroundColor = InformationBoxBlue,
                    borderColor = InformationBoxBlueBorder,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                )
            }
            if (viewModel.showDialog.value) {
                AlertDialogWithChoice(
                    setShowDialog = { viewModel.setShowDialog(it) },
                    title = "Select value to donate",
                    onConfirmButton = {
                        if (viewModel.makeDonation()
                        ) {
                            viewModel.setDonationSuccessDialog(true)
                        }
                        viewModel.setShowDialog(false)
                    }
                ) {
                    SingleChoicePicker(
                        items = viewModel.values.map { v -> v.Convert() + " €" },
                        selectedItem = viewModel.selectedValue.value,
                        setSelectedItem = { viewModel.setSelectedValue(it) },
                        textAlignment = Alignment.Start
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
                                    project.charityImage
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

    @Composable
    fun CharityRaisedColumn(
        actualSum: Double,
        goalSum: Double,
        modifier: Modifier,
        onButtonClick: () -> Unit
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Raised",
                style = MaterialTheme.typography.h6.copy(
                    color = TextDonationGray,
                    fontSize = 13.sp
                )
            )
            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = actualSum.Convert(),
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = " / " + goalSum.Convert() + "€",
                    style = MaterialTheme.typography.body1
                )
            }

            ProgressBar(
                value = actualSum,
                maxValue = goalSum,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )
            Button(
                modifier = Modifier
                    .padding(top = 16.dp, start = 48.dp, end = 48.dp)
                    .fillMaxWidth()
                    .height(64.dp),
                onClick = onButtonClick
            ) {
                Text(
                    text = stringResource(R.string.CharityDetailFragment_ButtonDonation),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}








