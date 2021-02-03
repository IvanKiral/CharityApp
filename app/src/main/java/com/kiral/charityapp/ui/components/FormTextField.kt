package com.kiral.charityapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
    password: Boolean = false
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .border(
                width = 1.dp,
                color = TextFieldBorder,
                shape = RoundedCornerShape(6.dp)
            )
    ) {
        TextField(
            textStyle = labelTextStyle,
            backgroundColor = Color.Transparent,
            value = text,
            inactiveColor = Color.Transparent,
            onValueChange = onChange,
            visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None,
            placeholder = {
                Text(
                    text = label,
                    style = labelTextStyle
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}