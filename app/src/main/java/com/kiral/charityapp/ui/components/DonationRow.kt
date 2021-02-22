package com.kiral.charityapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.theme.TextDonationGray

@Composable
fun DonationRow(
    price: String,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.preferredHeight(58.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.CharityDetailFragment_Raised),
                style = MaterialTheme.typography.body1.copy(fontSize = 13.sp),
                color = TextDonationGray
            )
            Text(
                text = price,
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
            )
        }
        Button(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxSize(),
            onClick = onButtonClick
        ) {
            Text(
                text = stringResource(R.string.CharityDetailFragment_ButtonDonation),
                style = MaterialTheme.typography.button
            )
        }
    }
}