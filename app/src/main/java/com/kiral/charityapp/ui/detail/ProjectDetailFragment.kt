package com.kiral.charityapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.kiral.charityapp.R
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.domain.model.Project
import com.kiral.charityapp.ui.components.*
import com.kiral.charityapp.ui.theme.*
import com.kiral.charityapp.utils.Convert
import com.kiral.charityapp.utils.DonationValues
import com.kiral.charityapp.utils.loadPicture
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.text.NumberFormat

@AndroidEntryPoint
class ProjectDetailFragment : Fragment() {

    val args: ProjectDetailFragmentArgs by navArgs()
    val viewModel: ProjectDetailViewModel by viewModels()
    private lateinit var project: Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getProject(args.projectId, args.email)
        project = viewModel.project!!
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
            project.charityImage.let { src ->
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
                    text = project.description,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )
                CharityRaisedColumn(
                    onButtonClick = { setDialog(true) },
                    modifier = Modifier.padding(top = 16.dp)
                )
                InformationBox(
                    text = buildAnnotatedString {
                         append(project.peopleDonated.toString() + " people")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)){
                            append(" and you ")
                        }
                        append("have donated to this project")
                        //append(stringResource(R.string.CharityDetailFragment_InformationBox, project.peopleDonated))
                    },
                    backgroundColor = InformationBoxBlue,
                    borderColor = InformationBoxBlueBorder,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                )
            }
            if(showDialog) {
                AlertDialogWithChoice(
                    values = values.map { v -> v.Convert() + " €"},
                    selectedValue = selectedValue,
                    setValue = setSelectedValue,
                    setShowDialog = setDialog,
                    title = "Select value to donate",
                    onConfirmButton = {
                        if(viewModel.makeDonation(project.charityId, project.donorId, project.id, values.get(selectedValue))){
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

    @Composable
    fun CharityRaisedColumn(
        modifier: Modifier,
        onButtonClick: () -> Unit
    ){
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
                    text = viewModel.actualSum.value.Convert(),
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = " / " + project.goalSum.Convert() + "€",
                    style = MaterialTheme.typography.body1
                )
            }

            ProgressBar(
                value = viewModel.actualSum.value,
                maxValue = project.goalSum,
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








