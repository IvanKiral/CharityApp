package com.kiral.charityapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kiral.charityapp.R
import com.kiral.charityapp.domain.charities
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.ui.theme.cardTextStyle
import com.kiral.charityapp.ui.theme.ProfileIconBorder
import com.kiral.charityapp.utils.loadPicture
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

enum class CharitiesScreen {
    Charities, Ranking
}

data class CharityGridItem(
    val imageUrl: Int,
    val text: String
)

@AndroidEntryPoint
class CharitiesFragment : Fragment() {

    private val viewModel: CharitiesViewModel by viewModels()

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CharitiesScreen()
            }
        }
    }

    @ExperimentalFoundationApi
    @Composable
    fun CharitiesScreen() {
        CharityTheme {
            var tabSelected by remember { mutableStateOf(CharitiesScreen.Charities) }
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                CharityAppBar(
                    tabSelected = tabSelected,
                    modifier = Modifier.fillMaxWidth(),
                    onProfileClick = {
                        findNavController().navigate(R.id.action_charitiesFragment_to_profileFragment)
                    },
                    onTabSelected = { tabSelected = it }
                )

                when (tabSelected) {
                    CharitiesScreen.Charities -> GridCharity(
                        lst = viewModel.charities,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    CharitiesScreen.Ranking -> RankingScreen()
                }
            }
        }
    }

    @Composable
    fun RankingScreen() {
    }

    @ExperimentalFoundationApi
    @Composable
    fun GridCharity(
        lst: List<Charity>,
        modifier: Modifier = Modifier
    ) {
        LazyVerticalGrid(
            GridCells.Fixed(2),
            modifier = modifier
        ) {
            items(lst) {
                CharityItem(
                    charity = it,
                    onClick = {
                        val action = CharitiesFragmentDirections
                            .actionCharitiesFragmentToCharityDetailFragment(it.id)
                        findNavController()
                            .navigate(action)
                    }
                )
            }
        }
    }

    @Composable
    fun CharityItem(
        charity: Charity,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 4.dp)
                .clickable(onClick = onClick)
        ) {
            charity.imgSrc.let { url ->
                val image = loadPicture(url = url, defaultImage = R.drawable.children).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .preferredHeight(110.dp)
                            .clip(RoundedCornerShape(5.dp))
                    )
                }

            }

            Text(
                text = charity.name,
                style = cardTextStyle,
                maxLines = 2,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

    }
}

@Composable
fun CharityAppBar(
    tabSelected: CharitiesScreen,
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit,
    onTabSelected: (CharitiesScreen) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Tabs(
            modifier = Modifier.weight(0.7f),
            titles = listOf(
                stringResource(R.string.CharitiesFragment_Charities),
                stringResource(R.string.CharitiesFragment_Rankings)
            ),
            tabSelected = tabSelected,
            onTabSelected = onTabSelected
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
        ) {
            IconRoundCorner(
                modifier = Modifier
                    .align(alignment = Alignment.CenterEnd),
                imageVector = vectorResource(id = R.drawable.ic_profile),
                onClick = onProfileClick
            )
        }
    }
}

@Composable
fun IconRoundCorner(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterEnd)
                .preferredSize(56.dp)
                .border(width = 1.dp, color = ProfileIconBorder, shape = CircleShape)
                .clip(shape = CircleShape)
                .clickable(onClick = onClick)
        ) {
            Image(
                imageVector = imageVector,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun Tabs(
    modifier: Modifier = Modifier,
    titles: List<String>,
    tabSelected: CharitiesScreen,
    onTabSelected: (CharitiesScreen) -> Unit
) {
    TabRow(
        selectedTabIndex = tabSelected.ordinal,
        modifier = modifier,
        contentColor = Color.Black,
        backgroundColor = Color.White,
        indicator = { },
        divider = { },
    ) {
        titles.forEachIndexed { index, title ->
            val selected = index == tabSelected.ordinal
            Tab(
                selected = selected,
                onClick = { onTabSelected(CharitiesScreen.values()[index]) },
            ) {
                Column() {
                    Text(
                        modifier = if (selected) Modifier else Modifier.padding(top = 4.dp),
                        style = if (selected) MaterialTheme.typography.h5
                        else MaterialTheme.typography.h5.copy(
                            fontSize = 20.sp,
                            color = Color.Black.copy(alpha = 0.3f)
                        ),
                        text = title,
                    )
                }
            }
        }
    }
}