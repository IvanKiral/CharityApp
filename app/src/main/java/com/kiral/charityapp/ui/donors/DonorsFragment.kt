package com.kiral.charityapp.ui.donors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.auth0.android.Auth0
import com.kiral.charityapp.R
import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.ui.components.ErrorScreen
import com.kiral.charityapp.ui.components.ProfileImageWithBorder
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.ui.theme.TextOptionSubtitle
import com.kiral.charityapp.utils.Constants.DONORS_PAGE_SIZE
import com.kiral.charityapp.utils.Convert
import com.kiral.charityapp.utils.Utils.loadPicture
import com.kiral.charityapp.utils.makeGravatarLink
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DonorsFragment : Fragment() {
    private val viewModel: DonorsViewModel by viewModels()
    val args: DonorsFragmentArgs by navArgs()

    @Inject
    lateinit var account: Auth0

    val USER_ID = intPreferencesKey("user_id")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getCharityDonors(args.charityId, 1)
        return ComposeView(requireContext()).apply {
            setContent {
                CharityTheme {
                    if (viewModel.error.value == null) {
                        DonorsScreen()
                    } else {
                        ErrorScreen(text = viewModel.error.value!!)
                    }
                }
            }
        }
    }

    @Composable
    fun DonorsScreen() {
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
                itemsIndexed(donorList.value) { index, donor ->
                    viewModel.indexPosition = index
                    if ((index + 1) >= (viewModel.page.value * DONORS_PAGE_SIZE) && !viewModel.loading.value) {
                        viewModel.nextPage(args.charityId)
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
            if (viewModel.loading.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }

    @Composable
    fun ProfileCard(
        imageBitmap: ImageBitmap?,
        name: String,
        badges: List<Badge>,
        donated: String,
        modifier: Modifier = Modifier
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(136.dp)
                .graphicsLayer(),
            elevation = 12.dp,
            shape = RoundedCornerShape(18.dp)
        ) {
            Row(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileImageWithBorder(
                    imageBitmap = imageBitmap,
                    borderMargin = 6.dp,
                    imageSize = 72.dp
                )
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth(0.7f)
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.body2
                    )
                    Row(modifier = Modifier.padding(top = 4.dp)) {
                        badges.take(3).forEachIndexed { i, b ->
                            Image(
                                imageVector = ImageVector.vectorResource(id = b.iconId),
                                contentDescription = "Donor badge icon",
                                contentScale = ContentScale.FillHeight,
                                modifier = if (i != 0) Modifier.height(18.dp).padding(start = 8.dp)
                                else Modifier.height(18.dp)
                            )
                        }
                        if(badges.size > 3) {
                            Text(
                                text = "+ ${(badges.size - 3)}",
                                style = MaterialTheme.typography.body1.copy(color = TextOptionSubtitle),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_heart),
                        contentDescription = "heart icon",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(top = 8.dp),
                    )
                    Text(
                        text = donated,
                        style = MaterialTheme.typography.body1.copy(color = TextOptionSubtitle),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }

}