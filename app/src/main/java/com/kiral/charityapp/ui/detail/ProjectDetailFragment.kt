package com.kiral.charityapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kiral.charityapp.domain.model.Project
import com.kiral.charityapp.ui.components.BaseScreen
import com.kiral.charityapp.ui.components.DonationField
import com.kiral.charityapp.ui.components.ExpandableText
import com.kiral.charityapp.ui.components.InformationBox
import com.kiral.charityapp.ui.detail.components.CharityRaisedColumn
import com.kiral.charityapp.ui.detail.components.DetailScreen
import com.kiral.charityapp.ui.detail.components.DonationFailedAlertDialog
import com.kiral.charityapp.ui.detail.components.DonationSuccessAlertDialog
import com.kiral.charityapp.ui.theme.BottomSheetShape
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.ui.theme.InformationBoxBlue
import com.kiral.charityapp.ui.theme.InformationBoxBlueBorder
import com.kiral.charityapp.ui.utils.buildInformationText
import com.kiral.charityapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectDetailFragment : Fragment() {

    private val args: ProjectDetailFragmentArgs by navArgs()
    private val viewModel: ProjectDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.getProject(args.projectId, args.donorId)
        super.onCreate(savedInstanceState)
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
                    onRetryClicked = { viewModel.getProject(args.projectId, args.donorId) }
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
                    DetailScreen(
                        imgSrc = p.charityImage,
                        donorDonated = p.donorDonated,
                        onClosePressed = requireActivity()::onBackPressed
                    ) {
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
                            shareLinkButtonClick = { Utils.shareLink(activity?.applicationContext!!) },
                            navController = findNavController()
                        )
                    }
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
    navController: NavController,
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
                text = project.charityAddress,
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
                onButtonClick = { viewModel.onExtraDonateButtonPressed() },
                modifier = Modifier.padding(top = 16.dp)
            )
            DonationField(
                loading = viewModel.donationLoading,
                shown = viewModel.showDonate,
                modifier = Modifier.padding(top = 16.dp),
                onButtonClick = { viewModel.onDonateButtonPressed(donorId, it) }
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
                    .fillMaxWidth(),
                onClick = {
                    val action = ProjectDetailFragmentDirections
                        .actionProjectDetailFragmentToDonorsFragment(
                            userId = donorId,
                            charityId = project.charityId,
                            projectId = project.id
                        )
                    navController.navigate(action)
                }
            )

            DonationSuccessAlertDialog(
                shown = viewModel.showDonationSuccessDialog,
                setShowDialog = { value -> viewModel.setDonationSuccessDialog(value) },
                sharePhotoButtonClick = sharePhotoButtonClick,
                shareLinkButtonClick = shareLinkButtonClick
            )

            DonationFailedAlertDialog(
                shown = viewModel.shouldShowDonationFailedDialog(),
                setShowDialog = { viewModel.donationError = null }
            )
        }
    }
}