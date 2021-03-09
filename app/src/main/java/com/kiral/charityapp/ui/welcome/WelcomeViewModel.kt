package com.kiral.charityapp.ui.welcome

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel
@Inject
constructor(
    val profileRepository: ProfileRepository
) : ViewModel() {
    @Inject
    lateinit var dataStore: DataStore<Preferences>

    val USER_ID = intPreferencesKey("user_id")

    var shouldNavigateToHomeFragment by mutableStateOf<Boolean?>(null)

    var donor_id: Int? = null

    var error by mutableStateOf<String?>(null)

    var email: String? = null

    fun getProfileId(email: String) {
        this.email = email
        profileRepository.login(email).onEach { state ->
            when (state) {
                is DataState.Success -> {
                    donor_id = state.data
                    shouldNavigateToHomeFragment = true
                    write_id(donor_id!!)
                }
                is DataState.HttpsErrorCode -> {
                    shouldNavigateToHomeFragment = false
                    error = state.message
                }
            }
        }.launchIn(viewModelScope)
    }

    suspend fun write_id(id: Int) {
        dataStore.edit { settings ->
            settings[USER_ID] = id
        }
    }
}