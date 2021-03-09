package com.kiral.charityapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.kiral.charityapp.ui.theme.TextFieldBorder
import com.kiral.charityapp.ui.theme.labelTextStyle

@Composable
fun FormTextField(
    text: String,
    onChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    borderColor: Color = TextFieldBorder,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .clip(RoundedCornerShape(6.dp))
            .border(
                width = 1.dp,
                color = borderColor,
                RoundedCornerShape(6.dp)
            )
    ) {
        TextField(
            value = text,
            onValueChange = onChange,
            textStyle = MaterialTheme.typography.body2,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            singleLine = true,
            placeholder = {
                Text(
                    text = label,
                    style = labelTextStyle
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        )
    }
}