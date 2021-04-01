package com.kiral.charityapp.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kiral.charityapp.R
import com.kiral.charityapp.utils.checkCurrencyFormat

@Composable
fun DonationField(
    loading: Boolean,
    buttonText: String,
    shown: Boolean,
    modifier: Modifier = Modifier,
    onButtonClick: (String) -> Unit = {}
) {
    val (value, setValue) = remember { mutableStateOf("") }
    val (error, setError) = remember { mutableStateOf(false) }
    Box(modifier = Modifier.animateContentSize()) {
        if (shown)
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    OutlinedTextField(
                        value = value,
                        enabled = !loading,
                        onValueChange = { str ->
                            setValue(str)
                            if (str.checkCurrencyFormat())
                                setError(false)
                            else
                                setError(true)
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
                            focusedLabelColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                        ),
                        isError = error,
                        textStyle = MaterialTheme.typography.body2,
                        modifier = Modifier.fillMaxWidth(0.6f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text(stringResource(R.string.donationField_textFieldLabel)) },
                        trailingIcon = { Text("€", style = MaterialTheme.typography.body2) }
                    )
                    Button(
                        enabled = !loading
                                && value.checkCurrencyFormat()
                                && value.isNotEmpty(),
                        onClick = { onButtonClick(value) },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp)
                            .height(56.dp),
                    ) {
                        Text(
                            text = buttonText,
                            style = MaterialTheme.typography.button,
                        )
                    }
                }
                Spacer(Modifier.height(20.dp))
                if (loading)
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                else {
                    Divider(
                        Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                }
            }
    }
}