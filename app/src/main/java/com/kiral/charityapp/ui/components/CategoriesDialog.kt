package com.kiral.charityapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringArrayResource
import com.kiral.charityapp.R

@Composable
fun CategoriesDialog(
    title: String,
    shown: Boolean,
    onDismiss: () -> Unit,
    onItemClick: (Int) -> Unit,
    categoriesSelected: SnapshotStateList<Boolean>,
    onConfirmButton: () -> Unit
) {
    AlertDialogWithChoice(
        title = title,
        shown = shown,
        onDismiss = onDismiss,
        onConfirmButton = onConfirmButton
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharitiesSelector(
                categories = stringArrayResource(id = R.array.Categories),
                categoriesSelected = categoriesSelected,
                onItemClick = onItemClick
            )
        }
    }
}