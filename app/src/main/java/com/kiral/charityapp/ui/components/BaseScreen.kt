package com.kiral.charityapp.ui.components

import androidx.compose.runtime.Composable
import com.kiral.charityapp.ui.theme.CharityTheme

@Composable
fun BaseScreen(
    loading: Boolean,
    error: String?,
    //modifier: Modifier = Modifier,
    onRetryClicked: () -> Unit = {},
    content: @Composable () -> Unit
) {
    CharityTheme {
        if (error == null) {
            if (loading) {
                LoadingScreen()
            } else {
                content()
            }
        } else {
            ErrorScreen(
                text = error,
                onButtonClicked = onRetryClicked
            )
        }
    }
}