package com.kiral.charityapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.components.ErrorScreen
import com.kiral.charityapp.ui.theme.CharityTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CharityTheme {
                    if (viewModel.error.value != null) {
                        ErrorScreen(text = viewModel.error.value!!){
                            viewModel.login()
                        }
                    }
                    if (viewModel.navigateToCharitiesFragment.value) {
                        findNavController().navigate(R.id.action_mainFragment_to_charitiesFragment)
                    } else if (viewModel.navigateToWelcomeFragment.value) {
                        findNavController().navigate(R.id.action_mainFragment_to_welcomeFragment)
                    }
                }
            }
        }
    }
}