package com.kiral.charityapp.ui.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.enums.DonationFrequency
import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.charities.ProfileRepository
import com.kiral.charityapp.ui.BaseApplication
import com.kiral.charityapp.utils.DonationValues
import com.kiral.charityapp.utils.badgesMap
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

    var badges = mutableListOf<Badge>()

    val loading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    val moneyValues = DonationValues
    val selectedMoney =  mutableStateOf(0)
    val frequencyValues = DonationFrequency.values().map { it.name }
    val selectedFrequency = mutableStateOf(0)

    val regularDonationDialog = mutableStateOf(false)

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
                    _profile.value?.let { p ->
                        badgesMap.forEach { (id, value) ->
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
                    loading.value = false
                    error.value = state.error
                }
            }
        }.launchIn(viewModelScope)
    }


    fun setActive(value: Boolean){
        _profile.value?.let { p ->
            profileRepository.updateRegularDonationActive(p.id, value).onEach { state ->
                when(state){
                    is DataState.Loading -> {}
                    is DataState.Success -> {
                        _profile.value = profile.value?.copy(
                            regularDonationActive = value
                        )
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
    }

    fun setRegion(value: String){
        _profile.value?.let { p ->
            profileRepository.updateRegion(p.id, value).onEach { state ->
                when(state){
                    is DataState.Loading -> {}
                    is DataState.Success -> {
                        _profile.value = profile.value?.copy(
                            region = value
                        )
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
    }

    fun setRegularPayment(userId: Int){
        val value = moneyValues.get(selectedMoney.value)
        profileRepository.updateRegularDonation(
            userId, true, value , selectedFrequency.value
        ).onEach { state ->
            when(state) {
                is DataState.Success -> {
                    _profile.value = _profile.value?.copy(
                        regularDonationActive = true,
                        regularDonationValue = value,
                        regularDonationFrequency = selectedFrequency.value
                    )
                }
            }
        }.launchIn(viewModelScope)
        setRegularDonationDialog(false)
    }

    fun setCategories(){
        //TODO: MAKE UI AND CALL FROM REPOSITORY
    }

    fun addCredit(){
        //TODO: MAKE UI AND CALL FROM REPOSITORY
    }

    fun setCountryDialog(value: Boolean){
        countryDialog.value = value
    }

    fun setSelectedFrequency(value: Int){
        selectedFrequency.value = value
    }

    fun setSelectedMoney(value: Int){
        selectedMoney.value = value
    }

    fun setRegularDonationDialog(value: Boolean){
        regularDonationDialog.value = value
    }
}