package com.kiral.charityapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kiral.charityapp.ui.theme.CherryRed

@Composable
fun StandardDialog(
    title: String,
    setDialog: (Boolean) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest = { setDialog(false) }) {
        Column(
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(color = Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp),
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h5.copy(fontSize = 20.sp, fontWeight = FontWeight.Normal),
                    textAlign = TextAlign.Justify
                )

            }
            Box(
                modifier = Modifier
                    .padding(top = 16.dp, start = 24.dp, end = 24.dp)
                    .sizeIn(128.dp)
                    .fillMaxWidth()
            ) {
                content()
            }
            Row(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 4.dp, end = 8.dp)
                    .height(36.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable(onClick = { setDialog(false) })
                )
                {
                    Text(
                        text = "CANCEL",
                        style = MaterialTheme.typography.body1.copy(color = Color.Black.copy(alpha = 0.6f)),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }
                Box(
                    modifier = Modifier
                        .width(84.dp)
                        .fillMaxHeight()
                        .clickable(onClick = onSubmit)
                )
                {
                    /*Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )*/
                    Text(
                        text = "OK",
                        style = MaterialTheme.typography.body1.copy(color = CherryRed),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}