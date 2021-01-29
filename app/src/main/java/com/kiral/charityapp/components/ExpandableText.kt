package com.kiral.charityapp.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ClickableText
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiral.charityapp.theme.ButtonBlue

private enum class TextState{
    Expanded, Collapsed
}

@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 5,
){
    val state = remember { mutableStateOf(TextState.Collapsed) }
    val showExpander = remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = text,
            maxLines = if (state.value == TextState.Collapsed) maxLines else Int.MAX_VALUE,
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.animateContentSize(animSpec = spring()),
            onTextLayout = {

                showExpander.value = it.didOverflowHeight || it.lineCount > maxLines
            }
        )
        if(showExpander.value) {
            ClickableText(
                text = if (state.value == TextState.Collapsed) AnnotatedString("Read more")
                        else AnnotatedString("Less"),
                modifier = Modifier.padding(top = 8.dp),
                style = MaterialTheme.typography.h6.copy(
                    color = ButtonBlue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                ),
                onClick = {
                    state.value =
                        if (state.value == TextState.Collapsed) TextState.Expanded else TextState.Collapsed
                }
            )
        }
    }

}