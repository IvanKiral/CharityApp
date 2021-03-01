package com.kiral.charityapp.ui.profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.repositories.charities.ProfileRepository
import com.kiral.charityapp.ui.BaseApplication
import com.kiral.charityapp.utils.getCountries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val application: BaseApplication,
    private val profileRepository: ProfileRepository
): AndroidViewModel(application) {

    //lateinit var profile: Profile
    private val _profile = mutableStateOf<Profile?>(null)
    val profile: State<Profile?>
        get() = _profile

    val active = mutableStateOf(_profile.value?.automaticDonations)

    val countryDialog = mutableStateOf(false)

    fun setProfile(id: Int){
        viewModelScope.launch {
            _profile.value = profileRepository.getProfile(id)
            Log.i("ProfileFragment", "here is value of profile ${profile.value?.name}")
        }
    }

    val countries = mutableStateOf(mapOf<String, String>())


    init {
        viewModelScope.launch {
            countries.value = getCountries(application.baseContext)
        }
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

    fun setCountryDialog(value: Boolean){
        countryDialog.value = value
    }
}