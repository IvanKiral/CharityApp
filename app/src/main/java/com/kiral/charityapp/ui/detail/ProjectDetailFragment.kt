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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.kiral.charityapp.domain.model.Project
import com.kiral.charityapp.ui.components.AlertDialogWithChoice
import com.kiral.charityapp.ui.components.BaseScreen
import com.kiral.charityapp.ui.components.ExpandableText
import com.kiral.charityapp.ui.components.InformationAlertDialog
import com.kiral.charityapp.ui.components.InformationBox
import com.kiral.charityapp.ui.components.SingleChoicePicker
import com.kiral.charityapp.ui.detail.components.CharityRaisedColumn
import com.kiral.charityapp.ui.detail.components.DetailHeader
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
class ProjectDetailFragment : Fragment() {

    private val args: ProjectDetailFragmentArgs by navArgs()
    private val viewModel: ProjectDetailViewModel by viewModels()

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
                BaseScreen(
                    error = viewModel.error,
                    loading = viewModel.loading,
                    onRetryClicked = {
                        viewModel.getProject(args.projectId, args.donorId)
                    }
                ) {
                    ProjectDetailScreen()
                }
            }
        }
    }

    @ExperimentalMaterialApi
    @Composable
    fun ProjectDetailScreen() {
        CharityTheme {
            Column {
                viewModel.project?.let { p ->
                    DetailHeader(
                        imgSrc = p.charityImage,
                        donorDonated = p.donorDonated,
                        onBackPressed = { requireActivity().onBackPressed() }
                    )
                    ProjectDetailBody(
                        project = p,
                        donorId = args.donorId,
                        viewModel = viewModel,
                        sharePhotoButtonClick = {
                            Utils.sharePhoto(
                                activity?.applicationContext!!,
                                p.charityImage
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


@Composable
fun ProjectDetailBody(
    project: Project,
    donorId: Int,
    viewModel: ProjectDetailViewModel,
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
                onButtonClick = { viewModel.showDialog = true },
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
                    textAlignment = Alignment.Start
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
                        Text("Share photo via", color = ButtonBlue)
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
                        Text("Share link via", color = ButtonBlue)
                    }
                }
            }
        }
    }
}







