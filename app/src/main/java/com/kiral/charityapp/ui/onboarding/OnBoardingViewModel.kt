package com.kiral.charityapp.ui.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.R
import com.kiral.charityapp.domain.enums.DonationFrequency
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.profile.ProfileRepository
import com.kiral.charityapp.ui.BaseApplication
import com.kiral.charityapp.ui.dataStore
import com.kiral.charityapp.utils.Constants.CATEGORIES_NUMBER
import com.kiral.charityapp.utils.Constants.DONATION_VALUES
import com.kiral.charityapp.utils.Utils.getCountries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

const val STATE_ONBOARDING_NAME_KEY = "onboarding_state_name"
const val STATE_ONBOARDING_COUNTRY_KEY = "onboarding_state_country"
const val STATE_ONBOARDING_CATEGORIES_KEY = "onboarding_state_country"
const val STATE_ONBOARDING_DONATION_VALUE_KEY = "onboarding_state_country"
const val STATE_ONBOARDING_DONATION_FREQUENCY_KEY = "onboarding_state_country"
const val STATE_ONBOARDING_COUNTRY_NAME_KEY = "onboarding_state_country_name"

@HiltViewModel
class OnBoardingViewModel
@Inject
constructor(
    private val application: BaseApplication,
    private val profileRepository: ProfileRepository,
    private val state: SavedStateHandle
) : AndroidViewModel(application) {
    private val USER_ID = intPreferencesKey("user_id")

    lateinit var profile: Profile
        private set

    var loading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var navigateToCharityFragment by mutableStateOf(false)

    var name by mutableStateOf("")
        private set
    var country by mutableStateOf("")
        private set
    var selectedCountry by mutableStateOf("")
        private set

    val c: Calendar = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
    var birthdayFieldText by mutableStateOf(application.getString(R.string.Onboarding_birthdayHint))

    private var categoriesList = List ( CATEGORIES_NUMBER ) { true }
    var selected = categoriesList.toMutableStateList()
        private set

    val intervalItems = DonationFrequency.values().map { it.name }
    var selectedInterval by mutableStateOf(0)
        private set

    var amountItems = DONATION_VALUES
    var selectedAmount by mutableStateOf(0)
        private set

    var countryDialog by mutableStateOf(false)

    var countries by mutableStateOf(mapOf<String, String>())

    var toastMessage = ""

    init {
        viewModelScope.launch {
            countries = getCountries(application.baseContext)
        }
        restoreState()
    }

    private fun restoreState(){
        state.get<String>(STATE_ONBOARDING_NAME_KEY)?.let{ n ->
            name = n
        }
        state.get<String>(STATE_ONBOARDING_COUNTRY_KEY)?.let{ c ->
            country = c
        }
        state.get<List<Boolean>>(STATE_ONBOARDING_CATEGORIES_KEY)?.let{ categories ->
             selected = categories.toMutableStateList()
        }
        state.get<Int>(STATE_ONBOARDING_DONATION_VALUE_KEY)?.let{ v ->
            selectedAmount = v
        }
        state.get<Int>(STATE_ONBOARDING_DONATION_FREQUENCY_KEY)?.let{ f ->
            selectedInterval = f
        }
        state.get<Int>(STATE_ONBOARDING_COUNTRY_NAME_KEY)?.let{ f ->
            selectedInterval = f
        }
    }

    fun createNewProfile(email: String) {
        profile = Profile(
            id = 0,
            email = email,
            name = "",
            donations = 0,
            birthday = Date(),
            categories = listOf(),
            region = "",
            credit = 0.0,
            regularDonationActive = false,
            regularDonationValue = 0.0,
            regularDonationFrequency = 1,
            badges = listOf()
        )
    }

    private fun addPersonalInformation(){
        profile.name = name
        profile.region = selectedCountry
    }

    fun addCategories() {
        profile.categories = selected
            .mapIndexedNotNull { index, v -> if (v) index + 1 else null }
    }

    private fun addRegularPayments(active: Boolean) {
        profile.regularDonationActive = active
        profile.regularDonationFrequency = selectedInterval
        profile.regularDonationValue = amountItems[selectedAmount]
    }

    fun register(skip: Boolean) {
        addPersonalInformation()
        addCategories()
        addRegularPayments(!skip)
        addBirthday()
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
                    loading = false
                    error = state.error
                }
            }
        }.launchIn(viewModelScope)
    }

    fun validateForm(): Boolean{
        val cal = Calendar.getInstance()
        cal.set(year, month, day)
        val givenDate = cal.time
        if(name.isBlank()){
            toastMessage = application.getString(R.string.editPersonalInformation_toastText)
            return false
        }
        if(givenDate >= c.time){
            toastMessage = application.getString(R.string.EditPersonalInformation_ToastBirthday)
            return false
        }
        return true
    }

    fun setNameValue(value: String){
        name = value
        state.set(STATE_ONBOARDING_NAME_KEY, name)
    }

    fun setCountryValue(iso: String, name: String){
        selectedCountry = iso
        country = name
        state.set(STATE_ONBOARDING_COUNTRY_KEY, selectedCountry)
        state.set(STATE_ONBOARDING_COUNTRY_NAME_KEY, country)
    }

    fun setCategories(index: Int){
        val selectedSize = selected.filter { b -> b }.size
        if (selectedSize > 0) {
            if (selectedSize > 1) {
                selected[index] = !selected[index]
            } else if (selectedSize == 1) {
                if (!selected[index])
                    selected[index] = !selected[index]
            }
        }
        state.set(STATE_ONBOARDING_CATEGORIES_KEY, selected.toList())
    }

    fun setDonationValue(value: Int){
        selectedAmount = value
        state.set(STATE_ONBOARDING_DONATION_VALUE_KEY, selectedAmount)
    }
    fun setDonationFrequency(value: Int){
        selectedInterval = value
        state.set(STATE_ONBOARDING_DONATION_VALUE_KEY, selectedInterval)
    }

    fun setBirthday(day: Int, month: Int, year: Int){
        this.day = day
        this.month = month
        this.year = year
        birthdayFieldText = "${day}.${month+1}.$year"
    }

    private fun addBirthday(){
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        profile.birthday = calendar.time
    }

    private suspend fun writeId(id: Int) {
        application.dataStore.edit { settings ->
            settings[USER_ID] = id
        }
    }
}