package com.kiral.charityapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiral.charityapp.R

@Composable
fun AlertDialogWithChoice(
    title: String,
    shown: Boolean,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onConfirmButton: () -> Unit = {},
    boxContent: @Composable () -> Unit = {}
) {
    if (shown) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.h5.copy(fontSize = 20.sp)
                )
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = { onDismiss() }) {
                        Text(
                            text = stringResource(R.string.alertDialog_cancel),
                            style = MaterialTheme.typography.button.copy(fontSize = 14.sp)
                        )
                    }
                    Button(
                        modifier = Modifier.padding(start = 8.dp),
                        onClick = onConfirmButton
                    ) {
                        Text(
                            text = stringResource(R.string.alertDialog_confirm),
                            style = MaterialTheme.typography.button.copy(fontSize = 14.sp)
                        )
                    }
                }
            },
            text = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    boxContent()
                }
            },
            modifier = modifier.fillMaxWidth(),
        )
    }
}