package com.kiral.charityapp.ui.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.components.ProgressBar
import com.kiral.charityapp.ui.theme.TextDonationGray
import com.kiral.charityapp.utils.convert

@Composable
fun CharityRaisedColumn(
    actualSum: Double,
    goalSum: Double,
    modifier: Modifier,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.detail_project_raised),
            style = MaterialTheme.typography.h6.copy(
                color = TextDonationGray,
                fontSize = 13.sp
            )
        )
        Row(
            modifier = Modifier.padding(top = 4.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = actualSum.convert(),
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = " / " + goalSum.convert() + "€",
                style = MaterialTheme.typography.body1
            )
        }

        ProgressBar(
            value = actualSum,
            maxValue = goalSum,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )
        Button(
            modifier = Modifier
                .padding(top = 16.dp, start = 48.dp, end = 48.dp)
                .fillMaxWidth()
                .height(64.dp),
            onClick = onButtonClick
        ) {
            Text(
                text = stringResource(R.string.detail_buttonExtraDonation),
                style = MaterialTheme.typography.button
            )
        }
    }
}