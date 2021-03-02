package com.kiral.charityapp.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
                SelectCharitiesScreen()
            }
        }
    }

    @Composable
    fun SelectCharitiesScreen() {
        val scrollState = rememberScrollState()
        CharityTheme() {
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(R.string.SelectCharitiesTypesFragment_Title),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                )

                CharitiesSelector(
                    categories = viewModel.categories,
                    categoriesSelected = viewModel.selected,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 64.dp)
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp)
                        .height(64.dp),
                    onClick = {
                        viewModel.addCharitiesTypes()
                        findNavController().navigate(R.id.action_selectCharitiesTypesFragment_to_setupRegularPaymentsFragment)
                    }
                ) {
                    Text("Continue", style = MaterialTheme.typography.button)
                }
            }
        }
    }
}