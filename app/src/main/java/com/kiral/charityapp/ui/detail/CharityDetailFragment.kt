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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.kiral.charityapp.R
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.ui.components.ExpandableText
import com.kiral.charityapp.ui.theme.*
import com.kiral.charityapp.utils.loadPicture
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharityDetailFragment : Fragment() {

    val args: CharityDetailFragmentArgs by navArgs()

    val viewModel: DetailViewModel by viewModels()
    lateinit var charity: Charity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        charity = viewModel.getCharity(args.charityId)
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
            charity.imgSrc.let { src ->
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
                DonationBox(
                    text = "You donated 1.5€",
                    backgroundColor = Color.Black.copy(alpha = 0.5f),
                )
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
                    text = charity.description,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )

                DonationRow(
                    price = "${charity.raised}€",
                    modifier = Modifier.padding(top = 24.dp)
                )

                InformationBox(
                    text = stringResource(R.string.CharityDetailFragment_InformationBox, charity.peopleDonated),
                    backgroundColor = InformationBoxBlue,
                    borderColor = InformationBoxBlueBorder,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun DonationBox(
    text: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .preferredHeight(24.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(backgroundColor)
    ) {
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
            Text(
                text = text,
                style = MaterialTheme.typography.button.copy(fontSize = 13.sp)
            )
        }
    }
}

@Composable
fun DonationRow(
    price: String,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.preferredHeight(58.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.CharityDetailFragment_Raised),
                style = MaterialTheme.typography.body1.copy(fontSize = 13.sp),
                color = TextDonationGray
            )
            Text(
                text = price,
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
            )
        }
        Button(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxSize(),
            onClick = onButtonClick
        ) {
            Text(
                text = stringResource(R.string.CharityDetailFragment_ButtonDonation),
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Composable
fun InformationBox(
    text: String,
    backgroundColor: Color,
    borderColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .preferredHeight(58.dp)
            .clip(RoundedCornerShape(1.dp))
            .background(backgroundColor)
            .border(width = 1.dp, color = borderColor)
    ) {
        Row() {
            Text(
                text = text,
                style = MaterialTheme.typography.body1.copy(fontSize = 13.sp)
            )
        }
    }
}
