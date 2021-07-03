package com.kiral.charityapp.ui.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kiral.charityapp.domain.model.CharityListItem
import com.kiral.charityapp.ui.home.CharitiesViewModel
import com.kiral.charityapp.utils.Constants

@ExperimentalFoundationApi
@Composable
fun CharityGrid(
    lst: List<CharityListItem>,
    modifier: Modifier = Modifier,
    state: LazyListState,
    viewModel: CharitiesViewModel,
    onItemClick: (Int) -> Unit,
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        state = state,
        modifier = modifier
    ) {
        itemsIndexed(lst) { index, item ->
            viewModel.setPosition(
                value = index,
                scrollPosition = state.firstVisibleItemIndex,
                scrollOffset = state.firstVisibleItemScrollOffset
            )
            if ((index + 1) >= (viewModel.page * Constants.CHARITIES_PAGE_SIZE) && !viewModel.charitiesPagingLoading) {
                viewModel.nextPage()
            }
            CharityItem(
                charity = item,
                onClick = {
                    onItemClick(item.id)
                }
            )
        }
    }
}