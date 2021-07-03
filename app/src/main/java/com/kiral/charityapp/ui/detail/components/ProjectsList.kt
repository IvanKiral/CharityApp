package com.kiral.charityapp.ui.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.kiral.charityapp.R
import com.kiral.charityapp.domain.model.CharityProject

@Composable
fun ProjectsList(
    projects: List<CharityProject>,
    navigateToProject: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.detail_charity_projects),
            style = MaterialTheme.typography.body1
        )
        projects.forEach { project ->
            ClickableText(
                text = AnnotatedString(project.name),
                style = MaterialTheme.typography.h5,
                onClick = { navigateToProject(project.id) },
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}