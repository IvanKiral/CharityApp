package com.kiral.charityapp.ui.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.repositories.charities.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val profileRepository: ProfileRepository
): ViewModel() {

    //lateinit var profile: Profile
    private val _profile = mutableStateOf<Profile?>(null)
    val profile: State<Profile?>
        get() = _profile

    val active = mutableStateOf(_profile.value?.automaticDonations)

    fun setProfile(id: Int){
        _profile.value = profileRepository.getProfile(id)
    }

    fun setActive(value: Boolean){
        _profile.value = _profile.value?.copy(automaticDonations = value)
    }

    fun setRegularPayment(value: Double, frequency:String){
        _profile.value = _profile.value?.copy(
            automaticDonations = true,
            automaticDonationsValue = value,
            automaticDonationTimeFrequency = frequency
        )
    }
}