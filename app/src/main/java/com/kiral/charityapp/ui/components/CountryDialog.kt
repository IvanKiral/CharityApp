package com.kiral.charityapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kiral.charityapp.R

@Composable
fun CountryDialog(
    isShown: Boolean,
    countries: Map<String, String>,
    setDialog: (Boolean) -> Unit,
    setCountry: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (isShown) {
        val countriesScrollState = rememberScrollState()
        Dialog(onDismissRequest = { setDialog(false) }) {
            Column(modifier = modifier.background(color = Color.White)) {
                Text(
                    text = stringResource(R.string.countryDialog_title),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(16.dp)
                        .verticalScroll(countriesScrollState)
                ) {
                    countries.forEach {
                        Box(
                            modifier = Modifier
                                .height(48.dp)
                                .fillMaxWidth()
                                .clickable {
                                    setCountry(it.key, it.value)
                                    setDialog(false)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = it.value,
                            )
                        }
                        Divider(modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}