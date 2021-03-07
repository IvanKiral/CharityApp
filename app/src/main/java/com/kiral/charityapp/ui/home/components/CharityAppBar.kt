package com.kiral.charityapp.ui.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.home.TabsNames

@ExperimentalFoundationApi
@Composable
fun CharityAppBar(
    tabSelected: TabsNames,
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit,
    onProfileLongClick: () -> Unit,
    onTabSelected: (TabsNames) -> Unit
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
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_profile),
                onClick = onProfileClick,
                onLongClick = onProfileLongClick
            )
        }
    }
}