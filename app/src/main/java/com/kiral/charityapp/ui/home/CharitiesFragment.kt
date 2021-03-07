package com.kiral.charityapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.kiral.charityapp.ui.components.CharitiesSelector
import com.kiral.charityapp.ui.components.ErrorScreen
import com.kiral.charityapp.ui.components.LeaderBoardItem
import com.kiral.charityapp.ui.components.LoadingScreen
import com.kiral.charityapp.ui.home.components.CharityAppBar
import com.kiral.charityapp.ui.home.components.CharityGrid
import com.kiral.charityapp.ui.theme.CharityTheme
import dagger.hilt.android.AndroidEntryPoint

enum class TabsNames {
    Charities, Ranking
}

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
    viewModel:CharitiesViewModel,
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
                onProfileClick = {
                    if (!viewModel.showFilter) {
                        val action =
                            CharitiesFragmentDirections.actionCharitiesFragmentToProfileFragment(
                                viewModel.userId
                            )
                        navController.navigate(action)
                    } else {
                        viewModel.apply{
                            getCharities()
                            showFilter = false
                        }
                    }
                },
                onProfileLongClick = {
                    viewModel.showFilter = true
                },
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
    if (viewModel.charitiesError != null) {
        ErrorScreen(text = viewModel.charitiesError!!)
    } else {
        if (viewModel.charitiesLoading) {
            LoadingScreen()
        } else {
            CharityGrid(
                lst = viewModel.charities,
                modifier = Modifier
                    .padding(top = 20.dp)
            ){ itemId ->
                val action = CharitiesFragmentDirections
                    .actionCharitiesFragmentToCharityDetailFragment(itemId, viewModel.userId)
                navController
                    .navigate(action)
            }
        }
    }
}

@Composable
fun RankingScreen(
    viewModel: CharitiesViewModel
) {
    if(viewModel.leaderboardError == null) {
        if(viewModel.leaderboardLoading){
            LoadingScreen()
        }else {
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
    else{
        ErrorScreen(text = viewModel.leaderboardError!!)
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
        Text(
            text = "Select charities to show",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.fillMaxWidth(),
        )
        CharitiesSelector(
            categories = viewModel.categories,
            categoriesSelected = viewModel.selectedCategories,
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}