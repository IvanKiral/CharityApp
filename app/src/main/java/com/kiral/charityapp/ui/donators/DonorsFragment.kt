package com.kiral.charityapp.ui.donators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.auth0.android.Auth0
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.components.ProfileImageWithBorder
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.ui.theme.TextOptionSubtitle
import com.kiral.charityapp.utils.Convert
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DonorsFragment : Fragment() {
    private val viewModel: DonorsViewModel by viewModels()

    @Inject
    lateinit var account: Auth0

    val USER_ID = intPreferencesKey("user_id")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CharityTheme {
                    DonorsScreen()
                }
            }
        }
    }

    @Composable
    fun DonorsScreen(){
        Column(modifier = Modifier
            .padding(16.dp)) {
            Text(
                text = "Donors",
                style = MaterialTheme.typography.h5
            )
            Divider(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
            )
            for(i in 0 .. 3) {
                ProfileCard(
                    imageBitmap = imageResource(id = R.drawable.rachel),
                    name = "Rachel Green",
                    donated = 100000.50.Convert(),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }

    @Composable
    fun ProfileCard(
        imageBitmap: ImageBitmap,
        name: String,
        donated: String,
        modifier: Modifier = Modifier
    ){
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(136.dp)
                .graphicsLayer(),
            elevation = 12.dp,
            shape = RoundedCornerShape(18.dp)
        ){
            Row(
                modifier = Modifier.padding(start = 16.dp, top= 16.dp, bottom = 16.dp),
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
                ){
                    Text(
                        text = name,
                        style = MaterialTheme.typography.body2
                    )
                    Row(modifier = Modifier.padding(top = 4.dp)){
                        for(i in 0..2) {
                            Image(
                                imageVector = vectorResource(id = R.drawable.ic_dog),
                                contentDescription = "",
                                modifier = if(i != 0) Modifier.padding(start = 8.dp) else Modifier
                            )
                        }
                        Text(
                            text = "+5",
                            style = MaterialTheme.typography.body1.copy(color = TextOptionSubtitle),
                            modifier = Modifier.padding(start = 8.dp)
                        )
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
                            imageVector = vectorResource(id = R.drawable.ic_heart),
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