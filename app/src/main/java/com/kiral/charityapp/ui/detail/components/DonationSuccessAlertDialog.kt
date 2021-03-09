package com.kiral.charityapp.ui.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kiral.charityapp.ui.components.InformationAlertDialog
import com.kiral.charityapp.ui.theme.CherryRed

@Composable
fun DonationSuccessAlertDialog (
    shown: Boolean,
    setShowDialog: (Boolean) -> Unit,
    sharePhotoButtonClick: () -> Unit,
    shareLinkButtonClick: () -> Unit,
) {
    InformationAlertDialog(
        title = "Thank you for your contribution!",
        shown = shown,
        buttonText = "Okay",
        setShowDialog = setShowDialog
    ) {
        Column {
            Text(
                "You did great today! Wanna share about your contribution?",
                textAlign = TextAlign.Justify
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                ),
                onClick = sharePhotoButtonClick
            ) {
                Text("Share photo via", color = CherryRed)
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                ),
                onClick = shareLinkButtonClick
            ) {
                Text("Share link via", color = CherryRed)
            }
        }
    }
}