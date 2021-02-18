package com.kiral.charityapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InformationAlertDialog(
    title: String,
    buttonText: String,
    setShowDialog: (Boolean) -> Unit,
    onConfirmButton: () -> Unit = {},
    content: @Composable () -> Unit = {},
){
    AlertDialog(
        onDismissRequest = {
            setShowDialog(false)
        },
        title = {
            Text(
                title,
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
                        text = buttonText,
                        style = MaterialTheme.typography.button.copy()
                    )
                }
        },
        text = {
            content()
        },
        modifier = Modifier.fillMaxWidth(),
    )
}