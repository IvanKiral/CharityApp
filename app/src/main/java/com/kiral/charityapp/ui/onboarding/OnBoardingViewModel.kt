package com.kiral.charityapp.ui.onboarding

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.enums.DonationFrequency
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.charities.ProfileRepository
import com.kiral.charityapp.ui.BaseApplication
import com.kiral.charityapp.utils.Constants.CATEGORIES
import com.kiral.charityapp.utils.Constants.DONATION_VALUES
import com.kiral.charityapp.utils.Utils.getCountries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel
@Inject
constructor(
    private val application: BaseApplication,
    private val profileRepository: ProfileRepository
): AndroidViewModel(application){

    val categories = CATEGORIES

    val loading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    lateinit var profile: Profile

    val name = mutableStateOf("")
    val country = mutableStateOf("")
    val selectedCountry = mutableStateOf("")
    val navigateToCharityFragment = mutableStateOf(false)

    val lst = MutableList(categories.size) { false }
    val selected = lst.toMutableStateList()

    val intervalItems = DonationFrequency.values().map { it.name }
    val selectedInterval = mutableStateOf(0)

    val amountItems = DONATION_VALUES
    val selectedAmount = mutableStateOf(0)

    val countryDialog = mutableStateOf(false)

    val countries = mutableStateOf(mapOf<String, String>())

    init {
        viewModelScope.launch {
            countries.value = getCountries(application.baseContext)
        }
    }

    fun createNewProfile(email: String){
        profile = Profile(
            id = 0,
            email = email,
            name = "",
            donations = 0,
            categories = listOf(),
            region = "",
            credit = 0.0,
            regularDonationActive = false,
            regularDonationValue = 0.0,
            regularDonationFrequency = 1,
            badges = listOf()
        )
    }

    fun addPersonalInformation(){
        profile.name = name.value
        profile.region = selectedCountry.value
    }

    fun addCharitiesTypes(){
        /*profile.categories = selected.foldIndexed(
            "",
            { index, result, sel ->
                if(sel) result + categories[index] + (if(index != selected.filter { it == true }.size - 1) ";" else "")
                else result + ""
            }
        )*/
    }

    fun addRegularPayments(){
        profile.regularDonationActive = if (amountItems.get(selectedAmount.value) > 0) true else false
        profile.regularDonationFrequency = selectedInterval.value
        profile.regularDonationValue = amountItems.get(selectedAmount.value)
    }

    fun register(){
        profileRepository.register(profile).onEach { state ->
            when(state){
                is DataState.Loading -> {
                    loading.value = true
                }
                is DataState.Success -> {
                    loading.value = false
                    navigateToCharityFragment.value = true
                }
                is DataState.Error -> {
                    error.value = state.error
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setName(value: String){
        name.value = value
    }

    fun setSelectedAmount(value: Int){
        selectedAmount.value = value
    }

    fun setSelectedInterval(value: Int){
        selectedInterval.value = value
    }

    fun setCountryDialog(value: Boolean){
        countryDialog.value = value
    }

    fun setCountry(value: String){
        country.value = value
    }

    fun setSelectedCountry(value: String){
        profile.region = value
        selectedCountry.value = value
    }
}