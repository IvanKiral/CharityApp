package com.kiral.charityapp.ui.onboarding

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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
class EditPersonalInformationFragment : Fragment() {
    private val args: EditPersonalInformationFragmentArgs by navArgs()
    private val viewModel: OnBoardingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val locale = getCurrentLocale(requireContext())
        viewModel.setCountryValue(locale.country, locale.displayCountry)
        viewModel.createNewProfile(args.email)
        return ComposeView(requireContext()).apply {
            setContent {
                EditInfoScreen(
                    viewModel = viewModel,
                    navigate = { findNavController()
                        .navigate(R.id.action_editPersonalInformationFragment_to_selectCharitiesTypesFragment) }
                )
            }
        }
    }
}


@Composable
fun EditInfoScreen(
    viewModel: OnBoardingViewModel,
    navigate: () -> Unit
) {
    val scrollState = rememberScrollState()

    CharityTheme {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
        ) {
            val ctx = LocalContext.current
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.editPersonalInformation_title),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center,
                )
                FormTextField(
                    text = viewModel.name,
                    onChange = { value -> viewModel.setNameValue(value) },
                    label = stringResource(R.string.editPersonalInformation_name_label),
                    modifier = Modifier.padding(top = 32
                        .dp, bottom = 8.dp)
                )
                BoxedText(
                    text = viewModel.country,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    viewModel.countryDialog = true
                }

                BoxedText(
                    text = viewModel.birthdayFieldText,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    val datePickerDialog = DatePickerDialog(
                        ctx, { datePicker: DatePicker, year: Int, month: Int, day: Int ->
                            viewModel.setBirthday(day, month, year)
                        },
                        viewModel.year, viewModel.month, viewModel.day
                    )
                    datePickerDialog.show()
                }

                CountryDialog(
                    countries = viewModel.countries,
                    isShown = viewModel.countryDialog,
                    setDialog = { value -> viewModel.countryDialog = value },
                    setCountry = { iso, name -> viewModel.setCountryValue(iso, name) },
                )
            }
            val context = LocalContext.current
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .height(56.dp),
                onClick = {
                    if (viewModel.validateForm())
                        navigate()
                    else {
                        Toast.makeText(
                            context,
                            viewModel.toastMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ) {
                Text(
                    stringResource(R.string.navigation_continue),
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}
