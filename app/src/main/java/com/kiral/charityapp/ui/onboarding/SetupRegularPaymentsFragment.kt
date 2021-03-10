package com.kiral.charityapp.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.kiral.charityapp.ui.components.SingleChoicePicker
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.utils.Convert
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetupRegularPaymentsFragment : Fragment() {
    private val viewModel: OnBoardingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                if (viewModel.navigateToCharityFragment) {
                    val action = SetupRegularPaymentsFragmentDirections
                        .actionSetupRegularPaymentsFragmentToCharitiesFragment(viewModel.profile.email)
                    findNavController()
                        .navigate(action)
                }
                SetupPaymentsScreen()
            }
        }
    }

    @Composable
    fun SetupPaymentsScreen() {
        val scrollState = rememberScrollState()
        CharityTheme {
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.setupRegularPayments_title),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                )
                Row(
                    modifier = Modifier.padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SingleChoicePicker(
                        items = viewModel.amountItems.map { i -> "${i.Convert()} €" },
                        selectedItem = viewModel.selectedAmount,
                        setSelectedItem = { value -> viewModel.selectedAmount = value },
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(horizontal = 8.dp),
                        textAlignment = Alignment.End
                    )
                    SingleChoicePicker(
                        items = viewModel.intervalItems,
                        selectedItem = viewModel.selectedInterval,
                        setSelectedItem = { value -> viewModel.selectedInterval = value },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        textAlignment = Alignment.Start
                    )
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .height(64.dp),
                    onClick = {
                        viewModel.addRegularPayments()
                        viewModel.register()
                    }
                ) {
                    Text(
                        text = if (viewModel.selectedAmount != 0) "Continue" else "Skip",
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    }
}