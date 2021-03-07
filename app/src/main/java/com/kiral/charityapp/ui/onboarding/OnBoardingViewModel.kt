package com.kiral.charityapp.ui.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.domain.enums.DonationFrequency
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.profile.ProfileRepository
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
    private val profileRepository: ProfileRepository,
    private var dataStore: DataStore<Preferences>
) : AndroidViewModel(application) {

    private val USER_ID = intPreferencesKey("user_id")

    val categories = CATEGORIES

    lateinit var profile: Profile
        private set

    var loading by mutableStateOf(true)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var navigateToCharityFragment by mutableStateOf(false)

    var name by mutableStateOf("")
    var country by mutableStateOf("")
    var selectedCountry by mutableStateOf("")

    private var categoriesList = MutableList(categories.size) { false }
    var selected = categoriesList.toMutableStateList()

    val intervalItems = DonationFrequency.values().map { it.name }
    var selectedInterval by mutableStateOf(0)

    var amountItems = DONATION_VALUES
    var selectedAmount by mutableStateOf(0)

    var countryDialog by mutableStateOf(false)

    var countries by mutableStateOf(mapOf<String, String>())

    init {
        viewModelScope.launch {
            countries = getCountries(application.baseContext)
        }
    }

    fun createNewProfile(email: String) {
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

    fun addPersonalInformation() {
        profile.name = name
        profile.region = selectedCountry
    }

    fun addCategories() {
        profile.categories = selected.mapIndexedNotNull { index, v -> if (v) index + 1 else null }
    }

    fun addRegularPayments() {
        profile.regularDonationActive = amountItems[selectedAmount] > 0
        profile.regularDonationFrequency = selectedInterval
        profile.regularDonationValue = amountItems[selectedAmount]
    }

    fun register() {
        profileRepository.register(profile).onEach { state ->
            when (state) {
                is DataState.Loading -> {
                    loading = true
                }
                is DataState.Success -> {
                    loading = false
                    navigateToCharityFragment = true
                    writeId(state.data)
                }
                is DataState.Error -> {
                    error = state.error
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun writeId(id: Int) {
        dataStore.edit { settings ->
            settings[USER_ID] = id
        }
    }
}