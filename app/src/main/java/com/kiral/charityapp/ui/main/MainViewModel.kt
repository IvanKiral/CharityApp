package com.kiral.charityapp.ui.main

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.callback.Callback
import com.auth0.android.result.Credentials
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.repositories.charities.ProfileRepository
import com.kiral.charityapp.utils.Auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    val profileRepository: ProfileRepository,
    val app: Application,
    val account: Auth0,
    val dataStore: DataStore<Preferences>
) : AndroidViewModel(app) {
    val USER_ID = intPreferencesKey("user_id")

    val navigateToCharitiesFragment = mutableStateOf(false)
    val navigateToWelcomeFragment = mutableStateOf(false)

    val error = mutableStateOf<String?>(null)
    val loading = mutableStateOf(false)
    var donor_id: Int? = null

    init {
        login()
    }

    fun login(){
        val client = AuthenticationAPIClient(account)
        val manager = CredentialsManager(client, SharedPreferencesStorage(app.applicationContext))
        if (manager.hasValidCredentials()) {
            val uId: Flow<Int> = dataStore.data
                .map { preferences ->
                    preferences[USER_ID] ?: -1
                }
            uId.onEach {
                if (it == -1) {
                    Auth.logout(account, app.applicationContext, dataStore)
                    navigateToWelcomeFragment.value = true
                } else {
                    manager.getCredentials(object :
                        Callback<Credentials, CredentialsManagerException> {
                        override fun onSuccess(result: Credentials) {
                            Auth.withUserEmail(account, result.accessToken, onFailFunction = {
                                error.value = "Was not able to connect you! Please try again later"
                            }) { email ->
                                getProfileId(email)
                            }
                        }

                        override fun onFailure(error: CredentialsManagerException) {
                            Log.i("WelcomeFragment", "Error has occured ${error}")
                        }
                    }
                    )
                }
            }.launchIn(viewModelScope)
        } else {
            navigateToWelcomeFragment.value = true
        }
    }

    fun getProfileId(email: String) {
        error.value = null
        profileRepository.login(email).onEach { state ->
            when (state) {
                is DataState.Success -> {
                    donor_id = state.data
                    loading.value = false
                    error.value = null
                    navigateToCharitiesFragment.value = true
                }
                is DataState.Loading -> {
                    loading.value = true
                }
                is DataState.Error -> {
                    error.value = state.error
                }
            }

        }.launchIn(viewModelScope)
    }
}