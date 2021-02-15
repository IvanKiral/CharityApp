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
fun AlertDialogWithChoice(
    values: List<String>,
    selectedValue: Int,
    setValue: (Int) -> Unit,
    title: String,
    setShowDialog: (Boolean) -> Unit,
    onConfirmButton: () -> Unit = {},
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = { setShowDialog(false) }) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.button.copy(fontSize = 14.sp)
                    )
                }
                Button(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = onConfirmButton
                ) {
                    Text(
                        text = "Confirm",
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
                SingleChoicePicker(
                    items = values,
                    selectedItem = selectedValue,
                    setSelectedItem = setValue,
                    textAlignment = Alignment.CenterHorizontally
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
    )
}