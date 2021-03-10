package com.kiral.charityapp.ui.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.enums.DonationFrequency
import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.profile.ProfileRepository
import com.kiral.charityapp.ui.BaseApplication
import com.kiral.charityapp.utils.Constants.BADGES
import com.kiral.charityapp.utils.Constants.CATEGORIES
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

    var regularDonationDialog by mutableStateOf(false)

    val moneyValues = DONATION_VALUES
    var selectedMoney by  mutableStateOf(0)

    var credit by mutableStateOf(false)
    var creditLoading by mutableStateOf(false)
    var creditError by mutableStateOf(false)

    val frequencyValues = DonationFrequency.values().map { it.name }
    var selectedFrequency by mutableStateOf(0)

    var selectedCategories = List(CATEGORIES.size) {false}.toMutableStateList()
    var categoryString by mutableStateOf("")

    var categoriesDialog by mutableStateOf(false)

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
                    profile?.categories?.forEach{v -> selectedCategories[v - 1] = true }
                    makeCategoryString()
                    profile?.let { p ->
                        BADGES.forEach { (id, value) ->
                            if(p.badges.contains(id)) {
                                badges.add(
                                    Badge(
                                        id = id,
                                        stringId = value.stringId,
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

    fun setCategories(){
        profile?.let { p ->
            val categoriesList = selectedCategories.mapIndexedNotNull{i, v -> if(v) i + 1 else null}
            profileRepository.updateCategories(p.id, categoriesList).onEach { state ->
                when(state){
                    is DataState.Loading -> {}
                    is DataState.Success -> {
                        profile = profile?.copy(
                            categories = categoriesList
                        )
                        makeCategoryString()
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
        categoriesDialog = false
    }

    fun onAddButtonClick(){
        credit = !credit
    }

    private fun makeCategoryString(){
        val categories = selectedCategories.mapIndexedNotNull{ i, v ->
            if(v) CATEGORIES[i].removeSuffix(" charity") else null
        }
        categoryString = categories.joinToString { it }
    }

    fun addCredit(value: String){
        profile?.let { p ->
            val profile_credit = p.credit
            profileRepository.addCredit(
                p.id, value.toDouble()
            ).onEach { state ->
                when (state) {
                    is DataState.Loading -> {
                        creditLoading = true
                    }
                    is DataState.Success -> {
                        creditLoading = false
                        profile = profile?.copy(
                            credit = profile_credit + value.toDouble()
                        )
                    }
                    else -> { }
                }
            }.launchIn(viewModelScope)
        }
    }
}