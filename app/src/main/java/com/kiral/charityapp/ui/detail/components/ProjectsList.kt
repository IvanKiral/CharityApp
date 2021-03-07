package com.kiral.charityapp.ui.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kiral.charityapp.domain.model.CharityProject
import com.kiral.charityapp.ui.detail.CharityDetailFragmentDirections

@Composable
fun ProjectsList(
    donorId: Int,
    projects: List<CharityProject>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Projects",
            style = MaterialTheme.typography.body1
        )
        projects.forEach { project ->
            ClickableText(
                text = AnnotatedString(project.name),
                style = MaterialTheme.typography.h5,
                onClick = {
                    val action = CharityDetailFragmentDirections
                        .actionCharityDetailFragmentToProjectDetailFragment(
                            project.id,
                            donorId
                        )
                    navController.navigate(action)
                },
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}