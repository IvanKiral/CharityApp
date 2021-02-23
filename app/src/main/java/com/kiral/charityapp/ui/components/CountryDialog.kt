package com.kiral.charityapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.unit.dp
import com.kiral.charityapp.utils.getCountries

@Composable
fun CountryDialog(
    isShown: Boolean,
    setDialog: (Boolean) -> Unit,
    setCountryText: (String) -> Unit,
    setCountry: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val countries = getCountries(AmbientContext.current)
    if (isShown) {
        val countriesScrollState = rememberScrollState()
        AlertDialogWithChoice(
            title = "Select your country",
            setShowDialog = { setDialog(it) },

            ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .verticalScroll(countriesScrollState)
            ) {
                countries.forEach {
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .fillMaxWidth()
                            .clickable {
                                setCountry(it.key)
                                setCountryText(it.value)
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