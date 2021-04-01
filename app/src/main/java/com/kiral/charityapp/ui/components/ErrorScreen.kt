package com.kiral.charityapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kiral.charityapp.R

@Composable
fun ErrorScreen(
    text: String,
    modifier: Modifier = Modifier,
    onButtonClicked: () -> Unit = {}
){
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = onButtonClicked
        ) {
            Text(stringResource(R.string.errorScreen_retry))
        }
    }
}