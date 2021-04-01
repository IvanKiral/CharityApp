package com.kiral.charityapp.ui.detail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiral.charityapp.R

@Composable
fun DonationFailedAlertDialog(
    shown: Boolean,
    setShowDialog: (Boolean) -> Unit,
) {
    if (shown) {
        AlertDialog(
            onDismissRequest = {
                setShowDialog(false)
            },
            title = {
                Text(
                    stringResource(R.string.detail_donationFailed_error),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.h5.copy(fontSize = 20.sp)
                )
            },
            buttons = {
                Button(
                    onClick = { setShowDialog(false) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.detail_donationFailed_confirm),
                        style = MaterialTheme.typography.button.copy()
                    )
                }
            },
        )
    }
}