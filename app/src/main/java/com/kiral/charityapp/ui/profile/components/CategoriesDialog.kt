package com.kiral.charityapp.ui.profile.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.components.AlertDialogWithChoice
import com.kiral.charityapp.ui.components.CharitiesSelector
import com.kiral.charityapp.utils.Constants.CATEGORIES

@Composable
fun CategoriesDialog(
    shown: Boolean,
    setShowDialog: (Boolean) -> Unit,
    categoriesSelected: SnapshotStateList<Boolean>,
    onConfirmButton: () -> Unit
) {
    AlertDialogWithChoice(
        title = stringResource(R.string.categoriesDialog_title),
        shown = shown,
        setShowDialog = setShowDialog,
        onConfirmButton = onConfirmButton
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CharitiesSelector(
                categories = CATEGORIES,
                categoriesSelected = categoriesSelected
            )
        }
    }
}