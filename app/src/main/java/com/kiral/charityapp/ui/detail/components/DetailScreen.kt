package com.kiral.charityapp.ui.detail.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun DetailScreen(
    imgSrc: String,
    donorDonated: Double,
    onClosePressed: () -> Unit,
    body: @Composable () -> Unit
) {
    Box {
        val configuration = LocalConfiguration.current
        DetailHeader(
            imgSrc = imgSrc,
            donorDonated = donorDonated,
            onBackPressed = onClosePressed
        )
        Column(modifier = Modifier.fillMaxSize()) {
            val bodyHeight = when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> 100.dp
                else -> 204.dp
            }
            Spacer(Modifier.height(bodyHeight))
            body()
        }
    }
}