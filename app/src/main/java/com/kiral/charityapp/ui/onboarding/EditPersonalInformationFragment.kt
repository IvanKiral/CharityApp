package com.kiral.charityapp.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
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
import com.kiral.charityapp.utils.getCountries
import com.kiral.charityapp.utils.getCurrentLocale
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditPersonalInformationFragment: Fragment(){

    val args: EditPersonalInformationFragmentArgs by navArgs()

    private val viewModel: OnBoardingViewModel by activityViewModels()

    private lateinit var countries: Map<String, String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val locale = getCurrentLocale(requireContext())
        viewModel.setCountry(locale.displayCountry)
        viewModel.createNewProfile(args.email)
        viewModel.setSelectedCountry(locale.country)
        countries = getCountries(requireContext())
        return ComposeView(requireContext()).apply {
            setContent {
                EditInfoScreen()
            }
        }
    }

    @Composable
    fun EditInfoScreen(){
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
                    text = stringResource(R.string.EditPersonalInformationFragment_Title),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                )

                FormTextField(
                    text = viewModel.name.value ,
                    onChange = { viewModel.setName(it) },
                    label = "Type your name",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                BoxedText(
                    text = viewModel.country.value,
                    modifier = Modifier.padding(vertical = 8.dp)
                ){
                    viewModel.setCountryDialog(true)
                }

                CountryDialog(
                    isShown = viewModel.countryDialog.value,
                    setDialog = { viewModel.setCountryDialog(it) },
                    setCountryText = { viewModel.setCountry(it) },
                    setCountry = { viewModel.setCountry(it) }
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .preferredHeight(64.dp),
                    onClick = {
                        viewModel.addPersonalInformation()
                        findNavController().navigate(R.id.action_editPersonalInformationFragment_to_selectCharitiesTypesFragment)
                    }
                ) {
                    Text("Continue", style = MaterialTheme.typography.button)
                }
            }
        }
    }
}
