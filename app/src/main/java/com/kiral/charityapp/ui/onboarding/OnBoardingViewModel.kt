package com.kiral.charityapp.ui.onboarding

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.kiral.charityapp.domain.enums.DonationFrequency
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.repositories.charities.ProfileRepository
import com.kiral.charityapp.utils.DonationValues
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel
@Inject
constructor(
    private val profileRepository: ProfileRepository
): ViewModel(){
    val categories = listOf(
        "Environment charity", "Animal charity",
        "Health charity", "Education charity", "Art and culture charity"
    )

    lateinit var profile: Profile

    val name = mutableStateOf("")
    val country = mutableStateOf("")
    val selectedCountry = mutableStateOf("")

    val lst = MutableList(categories.size) { false }
    val selected = lst.toMutableStateList()

    val intervalItems = DonationFrequency.values().map { it.name }
    val selectedInterval = mutableStateOf(0)

    val amountItems = DonationValues
    val selectedAmount = mutableStateOf(0)

    val countryDialog = mutableStateOf(false)

    fun createNewProfile(email: String){
        profile = Profile(
            email = email,
            name = "",
            donations = 0,
            charities = "",
            region = "",
            credit = 0.0,
            automaticDonations = false,
            automaticDonationsValue = 0.0,
            badges = listOf()
        )
    }

    fun addPersonalInformation(){
        profile.name = name.value
        profile.region = selectedCountry.value
    }

    fun addCharitiesTypes(){
        profile.charities = selected.foldIndexed(
            "",
            { index, result, sel ->
                if(sel) result + categories[index] + (if(index != selected.filter { it == true }.size - 1) ";" else "")
                else result + ""
            }
        )
    }

    fun addRegularPayments(){
        profile.automaticDonations = if (amountItems.get(selectedAmount.value) > 0) true else false
        profile.automaticDonationTimeFrequency = intervalItems.get(selectedInterval.value)
        profile.automaticDonationsValue = amountItems.get(selectedAmount.value)
    }

    fun register(){
        profileRepository.register(profile)
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
        selectedCountry.value = value
    }
}