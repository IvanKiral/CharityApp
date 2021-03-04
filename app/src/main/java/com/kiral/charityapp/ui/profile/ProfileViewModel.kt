package com.kiral.charityapp.ui.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.charities.ProfileRepository
import com.kiral.charityapp.ui.BaseApplication
import com.kiral.charityapp.utils.getCountries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val application: BaseApplication,
    private val profileRepository: ProfileRepository
): AndroidViewModel(application) {

    private val _profile = mutableStateOf<Profile?>(null)
    val profile: State<Profile?>
        get() = _profile

    val loading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    val active = mutableStateOf(_profile.value?.regularDonationActive)

    val countryDialog = mutableStateOf(false)

    val countries = mutableStateOf(mapOf<String, String>())

    init {
        viewModelScope.launch {
            countries.value = getCountries(application.baseContext)
        }
    }

    fun setProfile(id: Int){
        profileRepository.getProfile(id).onEach {  state ->
            when(state){
                is DataState.Loading -> loading.value = true
                is DataState.Success -> {
                    loading.value = false
                    _profile.value = state.data
                }
                is DataState.Error -> {
                    loading.value = false
                    error.value = state.error
                }
            }
        }.launchIn(viewModelScope)
    }


    fun setActive(value: Boolean){
        _profile.value = _profile.value?.copy(regularDonationActive = value)
    }

    fun setRegularPayment(userId: Int, value: Double){
        profileRepository.updateRegularDonation(
            userId, true, value ,1
        ).onEach { state ->
            when(state) {
                is DataState.Success -> {
                    _profile.value = _profile.value?.copy(
                        regularDonationActive = true,
                        regularDonationValue = value,
                        regularDonationFrequency = 1
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setCountryDialog(value: Boolean){
        countryDialog.value = value
    }
}