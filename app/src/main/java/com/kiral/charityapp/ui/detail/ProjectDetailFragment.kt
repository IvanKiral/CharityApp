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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kiral.charityapp.R
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
import com.kiral.charityapp.ui.theme.InformationBoxRed
import com.kiral.charityapp.ui.theme.InformationBoxRedBorder
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
                ProjectDetailScreen(
                    projectId = args.projectId,
                    donorId = args.donorId,
                    viewModel = viewModel,
                    onBackPressed = requireActivity()::onBackPressed,
                    navigateToDonors = { charityId ->
                        val action = ProjectDetailFragmentDirections
                            .actionProjectDetailFragmentToDonorsFragment(
                                userId = args.donorId,
                                charityId = charityId,
                                projectId = args.projectId
                            )
                        findNavController().navigate(action)
                    }
                )
            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun ProjectDetailScreen(
    projectId: Int,
    donorId: Int,
    viewModel: ProjectDetailViewModel,
    onBackPressed: () -> Unit,
    navigateToDonors: (Int) -> Unit,
) {
    CharityTheme {
        BaseScreen(
            error = viewModel.error,
            loading = viewModel.loading,
            onRetryClicked = { viewModel.getProject(projectId, donorId) }
        ) {
            Column {
                viewModel.project?.let { p ->
                    DetailScreen(
                        imgSrc = p.charityImage,
                        donorDonated = p.donorDonated,
                        onClosePressed = onBackPressed
                    ) {
                        val ctx = LocalContext.current
                        ProjectDetailBody(
                            project = p,
                            donorId = donorId,
                            viewModel = viewModel,
                            sharePhotoButtonClick = {
                                viewModel.addBadge(donorId)
                                Utils.sharePhoto(
                                    ctx,
                                    p.charityImage
                                )
                            },
                            shareLinkButtonClick = {
                                viewModel.addBadge(donorId)
                                Utils.shareLink(ctx)
                            },
                            navigateToDonors = {
                                navigateToDonors(p.charityId)
                            }
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
    navigateToDonors: () -> Unit,
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
                buttonText = stringResource(id = R.string.detail_donate),
                modifier = Modifier.padding(top = 16.dp),
                onButtonClick = { viewModel.onDonateButtonPressed(donorId, it) }
            )

            InformationBox(
                text = buildInformationText(
                    project.peopleDonated,
                    project.donorDonated,
                    stringResource(R.string.detail_project_peopleDonated)
                ),
                backgroundColor = InformationBoxRed,
                borderColor = InformationBoxRedBorder,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                onClick = navigateToDonors
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