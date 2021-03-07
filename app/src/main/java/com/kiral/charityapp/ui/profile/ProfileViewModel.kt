package com.kiral.charityapp.ui.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.enums.DonationFrequency
import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.profile.ProfileRepository
import com.kiral.charityapp.ui.BaseApplication
import com.kiral.charityapp.utils.Constants.BADGES
import com.kiral.charityapp.utils.Constants.DONATION_VALUES
import com.kiral.charityapp.utils.Utils.getCountries
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
    var profile by mutableStateOf<Profile?>(null)
        private set
    var badges = mutableListOf<Badge>()

    var loading by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    val moneyValues = DONATION_VALUES
    var selectedMoney by  mutableStateOf(0)
    val frequencyValues = DonationFrequency.values().map { it.name }
    var selectedFrequency by mutableStateOf(0)

    var regularDonationDialog by mutableStateOf(false)

    var countryDialog by mutableStateOf(false)
    var countries = mutableStateOf(mapOf<String, String>())

    init {
        viewModelScope.launch {
            countries.value = getCountries(application.baseContext)
        }
    }

    fun setProfile(id: Int){
        error = null
        profileRepository.getProfile(id).onEach {  state ->
            when(state){
                is DataState.Loading -> loading = true
                is DataState.Success -> {
                    loading = false
                    profile = state.data
                    profile?.let { p ->
                        BADGES.forEach { (id, value) ->
                            if(p.badges.contains(id)) {
                                badges.add(
                                    Badge(
                                        id = id,
                                        title = value.title,
                                        active = true,
                                        iconId = value.icon
                                    )
                                )
                            }
                        }
                    }
                }
                is DataState.Error -> {
                    loading = false
                    error = state.error
                }
            }
        }.launchIn(viewModelScope)
    }


    fun setActive(value: Boolean){
        profile?.let { p ->
            profileRepository.updateRegularDonationActive(p.id, value).onEach { state ->
                when(state){
                    is DataState.Loading -> {}
                    is DataState.Success -> {
                        profile = profile?.copy(
                            regularDonationActive = value
                        )
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
    }

    fun setRegion(value: String){
        profile?.let { p ->
            profileRepository.updateRegion(p.id, value).onEach { state ->
                when(state){
                    is DataState.Loading -> {}
                    is DataState.Success -> {
                        profile = profile?.copy(
                            region = value
                        )
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
    }

    fun setRegularPayment() {
        val value = moneyValues[selectedMoney]
        profile?.let { p ->
            Log.i("AppDebug", "profile id ${p.id}")
            profileRepository.updateRegularDonation(
                p.id, true, value, selectedFrequency
            ).onEach { state ->
                when (state) {
                    is DataState.Success -> {
                        profile = profile?.copy(
                            regularDonationActive = true,
                            regularDonationValue = value,
                            regularDonationFrequency = selectedFrequency
                        )
                    }
                }
            }.launchIn(viewModelScope)
            regularDonationDialog = false
        }
    }

//    fun setCategories(){
//        //TODO: MAKE UI AND CALL FROM REPOSITORY
//    }
//
//    fun addCredit(){
//        //TODO: MAKE UI AND CALL FROM REPOSITORY
//    }
//
//    fun setCountryDialog(value: Boolean){
//        countryDialog.value = value
//    }
//
//    fun setSelectedFrequency(value: Int){
//        selectedFrequency.value = value
//    }
//
//    fun setSelectedMoney(value: Int){
//        selectedMoney.value = value
//    }
//
//    fun setRegularDonationDialog(value: Boolean){
//        regularDonationDialog.value = value
//    }
}