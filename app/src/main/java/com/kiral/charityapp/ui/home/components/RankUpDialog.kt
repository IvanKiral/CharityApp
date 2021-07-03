package com.kiral.charityapp.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.components.InformationAlertDialog
import com.kiral.charityapp.ui.theme.CherryRed

@Composable
fun RankUpDialog (
    shown: Boolean,
    setShowDialog: (Boolean) -> Unit,
    shareLinkButtonClick: () -> Unit,
) {
    InformationAlertDialog(
        title = stringResource(R.string.RankUpDialog_Title),
        shown = shown,
        buttonText = stringResource(R.string.detail_donationSuccess_confirm),
        setShowDialog = setShowDialog
    ) {
        Column {
            Text(
                stringResource(R.string.RankUpDialog_description),
                textAlign = TextAlign.Justify
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                ),
                onClick = shareLinkButtonClick
            ) {
                Text(stringResource(R.string.detail_donationSuccess_shareLink), color = CherryRed)
            }
        }
    }
}