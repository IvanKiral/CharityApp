package com.kiral.charityapp.ui.components

import androidx.compose.foundation.InteractionState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun SingleChoicePicker(
    items: List<String>,
    selectedItem: Int,
    setSelectedItem: (Int) -> Unit,
    modifier: Modifier = Modifier,
    textAlignment: Alignment.Horizontal
){
    Column(
        modifier = modifier
    ) {
        items.forEachIndexed{ index, item ->
            Text(
                text = item,
                style = if (index == selectedItem) MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold )
                else MaterialTheme.typography.body2.copy(
                    color = Color.Black.copy(alpha = 0.5f)
                ),
                modifier = Modifier.clickable(
                    interactionState = InteractionState(),
                    indication = null,
                    onClick = { setSelectedItem(index) }
                )
                    .align(textAlignment)
            )
        }
    }

}