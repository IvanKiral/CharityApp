package com.kiral.charityapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.components.BaseScreen
import com.kiral.charityapp.ui.components.LeaderBoardItem
import com.kiral.charityapp.ui.home.components.CharityAppBar
import com.kiral.charityapp.ui.home.components.CharityGrid
import com.kiral.charityapp.ui.profile.components.CategoriesDialog
import com.kiral.charityapp.ui.theme.CharityTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                    viewModel = viewModel,
                    navigateToDetail = { itemId ->
                        val action = CharitiesFragmentDirections
                            .actionCharitiesFragmentToCharityDetailFragment(
                                itemId,
                                viewModel.userId
                            )
                        findNavController()
                            .navigate(action)
                    },
                    navigateToProfile = {
                        val action =
                            CharitiesFragmentDirections.actionCharitiesFragmentToProfileFragment(
                                viewModel.userId
                            )
                        findNavController().navigate(action)
                    }
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun CharitiesScreen(
    viewModel: CharitiesViewModel,
    navigateToDetail: (Int) -> Unit,
    navigateToProfile: () -> Unit,
) {
    CharityTheme {
        var tabSelected by remember { mutableStateOf(TabsNames.Charities) }
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            CharityAppBar(
                tabSelected = tabSelected,
                modifier = Modifier.fillMaxWidth(),
                filterOn = viewModel.filterIconHighlighted,
                onProfileClick = {
                    navigateToProfile()
                },
                onFilterClicked = { viewModel.changeShowFilter() },
                onTabSelected = { tabSelected = it }
            )
            when (tabSelected) {
                TabsNames.Charities -> CharityScreen(
                    viewModel,
                    navigateToDetail = navigateToDetail
                )
                TabsNames.Ranking -> RankingScreen(viewModel)
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun CharityScreen(
    viewModel: CharitiesViewModel,
    navigateToDetail: (Int) -> Unit,
) {
    BaseScreen(
        loading = viewModel.charitiesLoading,
        error = viewModel.charitiesError,
        onRetryClicked = {
            viewModel.getCharities(1)
        }
    ) {
        val scope = rememberCoroutineScope()
        val state = rememberLazyListState()
        CharityGrid(
            lst = viewModel.charities,
            state = state,
            modifier = Modifier
                .padding(top = 20.dp),
            viewModel = viewModel
        ) { itemId ->
            navigateToDetail(itemId)
        }
        viewModel.savedPosition?.let { (position, offset) ->
            scope.launch {
                state.scrollToItem(position, offset)
            }
            viewModel.savedPosition = null
        }

        CategoriesDialog(
            title = stringResource(R.string.CharitiesFragment_CategoriesDialogTitle),
            shown = viewModel.showFilter,
            setShowDialog = { viewModel.changeShowFilter() },
            onItemClick = { index -> viewModel.setCategories(index) },
            categoriesSelected = viewModel.selectedCategories,
            onConfirmButton = { viewModel.onFilterChange() }
        )
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
        Column {
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
