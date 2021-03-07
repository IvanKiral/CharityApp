package com.kiral.charityapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.theme.labelTextStyle

@Composable
fun CharitiesSelector(
    categories: List<String>,
    categoriesSelected: SnapshotStateList<Boolean>,
    modifier: Modifier = Modifier,
) {

    Column(modifier = modifier) {
        for (i in categories.indices) {
            RowSelector(
                text = categories[i],
                selected = categoriesSelected[i],
                onRowClick = {
                    val selectedSize = categoriesSelected.filter { b -> b == true }.size
                    if (selectedSize > 0) {
                        if (selectedSize > 1) {
                            categoriesSelected[i] = !categoriesSelected[i]
                        } else if (selectedSize == 1) {
                            if (!categoriesSelected[i])
                                categoriesSelected[i] = !categoriesSelected[i]
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun RowSelector(
    text: String = "",
    selected: Boolean = false,
    onRowClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onRowClick
            )
            .padding(bottom = 24.dp)
    ) {
        Text(
            text = text,
            style = if (selected) labelTextStyle else labelTextStyle.copy(
                color = labelTextStyle.color.copy(alpha = 0.6f)
            ),
            modifier = Modifier.weight(0.9f)
        )
        if (selected) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_selected),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}