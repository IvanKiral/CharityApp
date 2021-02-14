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
import androidx.navigation.fragment.navArgs
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.components.FormTextField
import com.kiral.charityapp.ui.detail.CharityDetailFragmentArgs
import com.kiral.charityapp.ui.theme.CharityTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditPersonalInformationFragment: Fragment(){

    val args: EditPersonalInformationFragmentArgs by navArgs()

    private val viewModel: OnBoardingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.createNewProfile(args.email)
        return ComposeView(requireContext()).apply {
            setContent {
                EditInfoScreen()
            }
        }
    }

    @Composable
    fun EditInfoScreen(){
        CharityTheme() {
            val (name, setName) = remember { mutableStateOf("")}
            Column(
                modifier = Modifier.padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.EditPersonalInformationFragment_Title),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                )

                FormTextField(
                    text = name ,
                    onChange = setName,
                    label = "Type your name",
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .preferredHeight(64.dp),
                    onClick = {
                        viewModel.addPersonalInformation("Test Test")
                        findNavController().navigate(R.id.action_editPersonalInformationFragment_to_selectCharitiesTypesFragment)
                    }
                ) {
                    Text("Continue", style = MaterialTheme.typography.button)
                }
            }
        }
    }
}
