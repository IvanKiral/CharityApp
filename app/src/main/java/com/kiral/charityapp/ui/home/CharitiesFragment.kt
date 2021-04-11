package com.kiral.charityapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.components.BaseScreen
import com.kiral.charityapp.ui.components.CharitiesSelector
import com.kiral.charityapp.ui.components.ClickableIcon
import com.kiral.charityapp.ui.components.LeaderBoardItem
import com.kiral.charityapp.ui.home.components.CharityAppBar
import com.kiral.charityapp.ui.home.components.CharityGrid
import com.kiral.charityapp.ui.theme.CharityTheme
import dagger.hilt.android.AndroidEntryPoint

enum class TabsNames {
    Charities, Ranking
}

@AndroidEntryPoint
class CharitiesFragment : Fragment() {
    private val viewModel: CharitiesViewModel by activityViewModels()

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CharitiesScreen(
                    viewModel,
                    findNavController()
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun CharitiesScreen(
    viewModel: CharitiesViewModel,
    navController: NavController
) {
    CharityTheme {
        var tabSelected by remember { mutableStateOf(TabsNames.Charities) }
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            CharityAppBar(
                tabSelected = tabSelected,
                modifier = Modifier.fillMaxWidth(),
                filterOn = viewModel.showFilter,
                onProfileClick = {
                    val action =
                        CharitiesFragmentDirections.actionCharitiesFragmentToProfileFragment(
                            viewModel.userId
                        )
                    navController.navigate(action)
                },
                onFilterClicked = { viewModel.onFilterChange() },
                onTabSelected = { tabSelected = it }
            )
            if (!viewModel.showFilter) {
                when (tabSelected) {
                    TabsNames.Charities -> CharityScreen(
                        viewModel,
                        navController
                    )
                    TabsNames.Ranking -> RankingScreen(viewModel)
                }
            } else {
                FilterScreen(viewModel)
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun CharityScreen(
    viewModel: CharitiesViewModel,
    navController: NavController,
) {
    BaseScreen(
        loading = viewModel.charitiesLoading,
        error = viewModel.charitiesError,
        onRetryClicked = {
            viewModel.getCharities()
        }
    ) {
        CharityGrid(
            lst = viewModel.charities,
            modifier = Modifier
                .padding(top = 20.dp),
            viewModel = viewModel
        ) { itemId ->
            val action = CharitiesFragmentDirections
                .actionCharitiesFragmentToCharityDetailFragment(itemId, viewModel.userId)
            navController
                .navigate(action)
        }
    }
}

@Composable
fun RankingScreen(
    viewModel: CharitiesViewModel
) {
    BaseScreen(
        loading = viewModel.leaderboardLoading,
        error = viewModel.leaderboardError,
        onRetryClicked = {
            viewModel.getLeaderboard()
        }
    ) {
        Column() {
            Text(
                text = "Rank: ${viewModel.donorRank}",
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.End
            )
            LazyColumn {
                itemsIndexed(viewModel.leaderboard) { index, item ->
                    LeaderBoardItem(
                        item = item,
                        index = index + 1
                    )
                }
            }
        }
    }
}

@Composable
fun FilterScreen(
    viewModel: CharitiesViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.charities_filter_title),
                style = MaterialTheme.typography.h5,
            )
            ClickableIcon(
                icon = ImageVector.vectorResource(id = R.drawable.ic_close_black),
                contentDescription = stringResource(id = R.string.back_icon_description),
                onIconClicked = {
                    viewModel.onFilterChange()
                },
                size = 24.dp
            )
        }
        CharitiesSelector(
            categories = stringArrayResource(id = R.array.Categories),
            categoriesSelected = viewModel.selectedCategories,
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}