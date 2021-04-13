package com.kiral.charityapp.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                SetupPaymentsScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun SetupPaymentsScreen(
    viewModel: OnBoardingViewModel
) {
    val scrollState = rememberScrollState()
    CharityTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(modifier = Modifier
                .clickable {
                    viewModel.register(true)
                }
            ) {
                Text(
                    text = "Skip",
                    style = MaterialTheme.typography.body2.copy(color = Color.Black.copy(alpha = 0.7f)),
                    modifier = Modifier.padding(16.dp)
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .verticalScroll(scrollState)
                    .align(Alignment.Center),
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
                        setSelectedItem = { value -> viewModel.setDonationValue(value)},
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(horizontal = 8.dp),
                        textAlignment = Alignment.End
                    )
                    SingleChoicePicker(
                        items = viewModel.intervalItems,
                        selectedItem = viewModel.selectedInterval,
                        setSelectedItem = { value -> viewModel.setDonationFrequency(value) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        textAlignment = Alignment.Start
                    )
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp, start = 24.dp, end = 24.dp)
                    .height(56.dp),
                onClick = { viewModel.register(false) }
            ) {
                Text(
                    text = stringResource(id = R.string.navigation_continue),
                    style = MaterialTheme.typography.button
                )
            }

            if(viewModel.loading){
                CircularProgressIndicator(
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            else if(viewModel.error != null){
                Text(
                    viewModel.error!!,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body2.copy(color = Color.Red),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}