package com.kiral.charityapp.ui.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
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
    viewModel: CharitiesViewModel,
    onItemClick: (Int) -> Unit,
) {
    LazyVerticalGrid(
        GridCells.Fixed(2),
        modifier = modifier
    ) {
        itemsIndexed(lst) { index, item ->
            viewModel.indexPosition = index
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