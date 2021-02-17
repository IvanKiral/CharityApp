package com.kiral.charityapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kiral.charityapp.R
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.ui.components.*
import com.kiral.charityapp.ui.theme.*
import com.kiral.charityapp.utils.Convert
import com.kiral.charityapp.utils.DonationValues
import com.kiral.charityapp.utils.loadPicture
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharityDetailFragment : Fragment() {

    val args: CharityDetailFragmentArgs by navArgs()

    val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCharity(id = args.charityId, donorEmail = args.donorEmail)
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
                CharityDetailHeader()
                CharityDetailBody(modifier = Modifier.offset(y = -20.dp))
            }
        }
    }

    @Composable
    fun CharityDetailHeader() {
        Box() {
            viewModel.charity.value.imgSrc.let { src ->
                val image = loadPicture(url = src , defaultImage = R.drawable.children)
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
                if(viewModel.donorDonated.value > 0) {
                    DonationBox(
                        text = "You donated ${viewModel.donorDonated.value.Convert()}  €",
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
        modifier: Modifier = Modifier
    ) {
        val values = DonationValues
        val (selectedValue, setSelectedValue) = remember { mutableStateOf(0) }
        val (showDialog, setDialog) = remember { mutableStateOf(false ) }
        val (showDonationSuccessDialog, setDonationSuccessDialog) = remember { mutableStateOf(false ) }
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
                    text = viewModel.charity.value.name,
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
                    text = viewModel.charity.value.address,
                    style = MaterialTheme.typography.body1
                )

                ExpandableText(
                    text = viewModel.charity.value.description,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )

                DonationRow(
                    price = "${viewModel.raised.value.toDouble().Convert()}€",
                    modifier = Modifier.padding(top = 24.dp),
                    onButtonClick = {
                        setDialog(true)
                    }
                )

                InformationBox(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.CharityDetailFragment_InformationBox,
                            viewModel.charity.value.peopleDonated))
                    },
                    backgroundColor = InformationBoxBlue,
                    borderColor = InformationBoxBlueBorder,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                )
                ProjectsColumn(
                    modifier = Modifier.padding(top = 16.dp)
                )
                if(showDialog) {
                    AlertDialogWithChoice(
                        values = values.map { v -> v.Convert() + " €" },
                        selectedValue = selectedValue,
                        setValue = setSelectedValue,
                        setShowDialog = setDialog,
                        title = "Select value to donate",
                        onConfirmButton = {
                            if(viewModel.makeDonation(viewModel.charity.value.id,
                                    viewModel.charity.value.donorId,
                                    values.get(selectedValue))
                            ){
                                setDonationSuccessDialog(true)
                            }
                            setDialog(false)
                        }
                    )
                }
                if(showDonationSuccessDialog){
                    InformationAlertDialog(
                        title = "Thank you for your contribution!",
                        buttonText = "Okay" ,
                        setShowDialog = setDonationSuccessDialog
                    ){
                        Text("You did great today. Wanna share about your contribution?")
                    }
                }
            }
        }
    }

    @Composable
    fun ProjectsColumn(
        modifier: Modifier = Modifier
    ){
        Column(modifier = modifier) {
            Text(
                text = "Projects",
                style = MaterialTheme.typography.body1
            )
            viewModel.charity.value.projects.forEach{ project ->
                ClickableText(
                    text = AnnotatedString(project.name),
                    style = MaterialTheme.typography.h5,
                    onClick = {
                        val action = CharityDetailFragmentDirections
                            .actionCharityDetailFragmentToProjectDetailFragment(project.id, args.donorEmail)
                        findNavController().navigate(action)
                    },
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }
    }
}
