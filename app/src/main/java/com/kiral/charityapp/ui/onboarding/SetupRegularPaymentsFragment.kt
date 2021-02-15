package com.kiral.charityapp.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
class SetupRegularPaymentsFragment: Fragment(){

    private val viewModel: OnBoardingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SetupPaymentsScreen()
            }
        }
    }

    @Composable
    fun SetupPaymentsScreen(){
        CharityTheme() {
            Column(
                modifier = Modifier.padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val intervalItems = listOf("daily", "weekly", "monthly")
                val (selectedInterval, setInterval) = remember { mutableStateOf(0)}

                val amountItems = listOf(0.0, 0.5, 1.0, 2.0, 5.0, 10.0, 20.0, 50.0, 100.0, 500.0, 1000.0)
                val (selectedAmount, setAmount) = remember { mutableStateOf(0)}

                Text(
                    text = stringResource(R.string.SetupRegularPaymentsFragment_Title),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                )
                Row(
                    modifier = Modifier.padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SingleChoicePicker(
                        items = amountItems.map { i -> "${i.Convert()} €" },
                        selectedItem = selectedAmount,
                        setSelectedItem = setAmount,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(horizontal = 8.dp),
                        textAlignment = Alignment.End
                    )
                    SingleChoicePicker(
                        items = intervalItems,
                        selectedItem = selectedInterval,
                        setSelectedItem = setInterval,
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
                        .preferredHeight(64.dp),
                    onClick = {
                        viewModel.addRegularPayments(amountItems.get(selectedAmount), intervalItems.get(selectedInterval))
                        viewModel.register()
                        val action = SetupRegularPaymentsFragmentDirections
                            .actionSetupRegularPaymentsFragmentToCharitiesFragment(viewModel.profile.email)
                        findNavController()
                            .navigate(action)
                    }
                ) {
                    Text(
                        text = if (selectedAmount != 0) "Continue" else "Skip",
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }
    }

}


