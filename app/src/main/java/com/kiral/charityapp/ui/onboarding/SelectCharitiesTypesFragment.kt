package com.kiral.charityapp.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.InteractionState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.fragment.findNavController
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.ui.theme.labelTextStyle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectCharitiesTypesFragment : Fragment() {

    private val viewModel: OnBoardingViewModel by activityViewModels()

    private val categories = listOf(
        "Environment charity", "Animal charity",
        "Health charity", "Education charity", "Art and culture charity"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SelectCharitiesScreen()
            }
        }
    }

    @Composable
    fun SelectCharitiesScreen() {
        val lst = MutableList(categories.size) { false }
        val selected = remember { lst.toMutableStateList() }

        CharityTheme() {
            Column(
                modifier = Modifier.padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(R.string.SelectCharitiesTypesFragment_Title),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                )

                CharitiesSelector(
                    categories = categories,
                    categoriesSelected = selected,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 64.dp)
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp)
                        .preferredHeight(64.dp),
                    onClick = {
                        val charities = selected.foldIndexed(
                            "",
                            { index, result, sel ->
                                if(sel) result + categories[index] + (if(index != selected.filter { it == true }.size - 1) ";" else "")
                                else result + ""
                            }
                        )
                        viewModel.addCharitiesTypes(charities)
                        findNavController().navigate(R.id.action_selectCharitiesTypesFragment_to_setupRegularPaymentsFragment)
                    }
                ) {
                    Text("Continue", style = MaterialTheme.typography.button)
                }
            }
        }
    }

    @Composable
    fun CharitiesSelector(
        categories: List<String>,
        categoriesSelected: SnapshotStateList<Boolean>,
        modifier: Modifier = Modifier,
    ) {

        Column(modifier = modifier) {
            for (i in 0 until categories.size) {
                RowSelector(
                    text = categories[i],
                    selected = categoriesSelected[i],
                    onRowClick = {categoriesSelected[i] = !categoriesSelected[i]}
                )
            }
        }
    }

    @Composable
    fun RowSelector(
        text: String = "",
        selected: Boolean = false,
        onRowClick: () -> Unit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionState = InteractionState(),
                    onClick = onRowClick
                )
                .padding(bottom = 24.dp)
        ) {
            Text(
                text = text,
                style = if (selected) labelTextStyle else labelTextStyle.copy(
                    color = labelTextStyle.color.copy(alpha = 0.6f)
                ),
                modifier = Modifier.weight(0.9f)
            )
            if (selected) {
                Image(
                    imageVector = vectorResource(id = R.drawable.ic_selected),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}