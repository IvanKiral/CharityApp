package com.kiral.charityapp.ui.donors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.auth0.android.Auth0
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.components.ErrorScreen
import com.kiral.charityapp.ui.donors.components.ProfileCard
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.utils.Constants.DONORS_PAGE_SIZE
import com.kiral.charityapp.utils.Convert
import com.kiral.charityapp.utils.Utils.loadPicture
import com.kiral.charityapp.utils.makeGravatarLink
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DonorsFragment : Fragment() {
    private val viewModel: DonorsViewModel by viewModels()
    private val args: DonorsFragmentArgs by navArgs()

    @Inject
    lateinit var account: Auth0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getCharityDonors(args.charityId, 1)
        return ComposeView(requireContext()).apply {
            setContent {
                CharityTheme {
                    if (viewModel.error == null) {
                        DonorsScreen(viewModel, args.charityId)
                    } else {
                        ErrorScreen(text = viewModel.error!!){
                            viewModel.retry(args.charityId)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DonorsScreen(
    viewModel:DonorsViewModel,
    charityId: Int
) {
    val donorList = viewModel.charityDonors
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Text(
                    text = "Donors",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                )
                Divider(
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                )
            }
            itemsIndexed(donorList) { index, donor ->
                viewModel.indexPosition = index
                if ((index + 1) >= (viewModel.page * DONORS_PAGE_SIZE) && !viewModel.loading) {
                    viewModel.nextPage(charityId)
                }
                val topPadding = if (index == 0) 16.dp else 8.dp
                ProfileCard(
                    imageBitmap = donor.email.let { e ->
                        val img = loadPicture(
                            url = e.makeGravatarLink(),
                            defaultImage = R.drawable.ic_loading_photo
                        )
                        img.value?.asImageBitmap()
                    },
                    name = donor.name,
                    badges= donor.badges,
                    donated = donor.donated.Convert(),
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = topPadding,
                        bottom = 8.dp
                    )
                )

            }
        }
        if (viewModel.loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}