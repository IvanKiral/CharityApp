package com.kiral.charityapp.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import androidx.navigation.fragment.navArgs
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.components.BoxedText
import com.kiral.charityapp.ui.components.CountryDialog
import com.kiral.charityapp.ui.components.FormTextField
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.utils.Utils.getCurrentLocale
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditPersonalInformationFragment: Fragment(){
    private val args: EditPersonalInformationFragmentArgs by navArgs()
    private val viewModel: OnBoardingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val locale = getCurrentLocale(requireContext())
        viewModel.country = locale.displayCountry
        viewModel.createNewProfile(args.email)
        viewModel.selectedCountry = locale.country
        return ComposeView(requireContext()).apply {
            setContent {
                EditInfoScreen()
            }
        }
    }

    @Composable
    fun EditInfoScreen(){
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
                    text = stringResource(R.string.EditPersonalInformationFragment_Title),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                )

                FormTextField(
                    text = viewModel.name ,
                    onChange = { value -> viewModel.name = value },
                    label = "Type your name",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                BoxedText(
                    text = viewModel.country,
                    modifier = Modifier.padding(vertical = 8.dp)
                ){
                    viewModel.countryDialog = true
                }

                CountryDialog(
                    countries = viewModel.countries,
                    isShown = viewModel.countryDialog,
                    setDialog = { value -> viewModel.countryDialog = value },
                    setCountryText = { value -> viewModel.country = value },
                    setCountry = { value -> viewModel.selectedCountry = value },
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .height(64.dp),
                    onClick = {
                        if(viewModel.addPersonalInformation())
                            findNavController()
                                .navigate(R.id.action_editPersonalInformationFragment_to_selectCharitiesTypesFragment)
                        else{
                            Toast.makeText(requireContext(), "Please fill up your name", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("Continue", style = MaterialTheme.typography.button)
                }
            }
        }
    }
}
