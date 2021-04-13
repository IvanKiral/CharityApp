package com.kiral.charityapp.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.components.CharitiesSelector
import com.kiral.charityapp.ui.theme.CharityTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectCharitiesTypesFragment : Fragment() {

    private val viewModel: OnBoardingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SelectCharitiesScreen(
                    viewModel = viewModel,
                    navigate = {
                        findNavController()
                            .navigate(R.id.action_selectCharitiesTypesFragment_to_setupRegularPaymentsFragment)
                    }
                )
            }
        }
    }
}

@Composable
fun SelectCharitiesScreen(
    viewModel: OnBoardingViewModel,
    navigate: () -> Unit
) {
    val scrollState = rememberScrollState()
    CharityTheme {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(R.string.selectCharitiesTypes_title),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                )

                CharitiesSelector(
                    categories = stringArrayResource(id = R.array.Categories),
                    categoriesSelected = viewModel.selected,
                    onItemClick = { index -> viewModel.setCategories(index) },
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 32.dp)
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .height(56.dp),
                onClick = {
                    viewModel.addCategories()
                    navigate()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.navigation_continue),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}