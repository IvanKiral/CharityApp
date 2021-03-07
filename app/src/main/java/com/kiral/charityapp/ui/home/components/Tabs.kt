package com.kiral.charityapp.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiral.charityapp.ui.home.TabsNames

@Composable
fun Tabs(
    modifier: Modifier = Modifier,
    titles: List<String>,
    tabSelected: TabsNames,
    onTabSelected: (TabsNames) -> Unit
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
                onClick = { onTabSelected(TabsNames.values()[index]) },
            ) {
                Column {
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