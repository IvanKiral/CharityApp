package com.kiral.charityapp.ui.onboarding

import androidx.lifecycle.ViewModel
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.domain.profiles
import com.kiral.charityapp.repositories.charities.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel
@Inject
constructor(
    private val profileRepository: ProfileRepository
): ViewModel(){
    lateinit var profile: Profile

    fun createNewProfile(email: String){
        profile = Profile(
            email = email,
            name = "",
            donations = 0,
            charities = "",
            credit = 0f,
            automaticDonations = false,
            automaticDonationsValue = 0,
            badges = listOf()
        )
    }

    fun addPersonalInformation(name: String){
        profile.name = name
    }

    fun addCharitiesTypes(charities: String){
        profile.charities = charities
    }

    fun addRegularPayments(value: Int, frequency: String){
        profile.automaticDonationTimeFrequency = frequency
        profile.automaticDonationsValue = value
    }

    fun register(){
        profile.id = profiles.size
        profileRepository.register(profile)
    }
}