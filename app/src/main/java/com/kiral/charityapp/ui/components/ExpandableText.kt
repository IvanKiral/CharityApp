package com.kiral.charityapp.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.theme.CherryRed

private enum class TextState{
    Expanded, Collapsed
}

@Composable
fun ExpandableText(
    text: AnnotatedString,
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
            modifier = Modifier.animateContentSize(),
            onTextLayout = {

                showExpander.value = it.didOverflowHeight || it.lineCount > maxLines
            }
        )
        if(showExpander.value) {
            ClickableText(
                text = if (state.value == TextState.Collapsed) AnnotatedString(stringResource(R.string.expandableText_readMore))
                        else AnnotatedString(stringResource(R.string.expandableText_less)),
                modifier = Modifier.padding(top = 8.dp),
                style = MaterialTheme.typography.h6.copy(
                    color = CherryRed,
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