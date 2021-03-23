package com.kiral.charityapp.ui.welcome

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.profile.ProfileRepository
import com.kiral.charityapp.ui.dataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel
@Inject
constructor(
    val app: Application,
    val profileRepository: ProfileRepository
) : AndroidViewModel(app) {
    private val USER_ID = intPreferencesKey("user_id")

    var shouldNavigateToHomeFragment by mutableStateOf(false)
    var shouldNavigateToEditPersonalInformationFragment by mutableStateOf(false)

    var donor_id: Int? = null

    var loading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    var email: String? = null

    fun getProfileId(email: String) {
        error = null
        this.email = email
        profileRepository.login(email).onEach { state ->
            loading = false
            when (state) {
                is DataState.Loading -> {
                    loading = true
                }
                is DataState.Success -> {
                    donor_id = state.data
                    shouldNavigateToHomeFragment = true
                    writeId(donor_id!!)
                }
                is DataState.HttpsErrorCode -> {
                    when(state.code) {
                        404 -> shouldNavigateToEditPersonalInformationFragment = true
                        else -> {
                            error = state.message
                        }
                    }
                }
                is DataState.Error -> {
                    error = state.error
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun writeId(id: Int) {
        app.dataStore.edit { settings ->
            settings[USER_ID] = id
        }
    }
}