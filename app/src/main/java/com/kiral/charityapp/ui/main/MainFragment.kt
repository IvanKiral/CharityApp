package com.kiral.charityapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.callback.Callback
import com.auth0.android.result.Credentials
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.components.ErrorScreen
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.utils.Auth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private val USER_ID = intPreferencesKey("user_id")

    @Inject
    lateinit var account: Auth0
    @Inject
    lateinit var dataStore: DataStore<Preferences>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        login()
        return ComposeView(requireContext()).apply {
            setContent {
                CharityTheme {
                    if (viewModel.error != null) {
                        ErrorScreen(text = viewModel.error!!){
                           login()
                        }
                    }
                    if (viewModel.navigateToCharitiesFragment) {
                        viewModel.navigateToCharitiesFragment = false
                        findNavController().navigate(R.id.action_mainFragment_to_charitiesFragment)
                    } else if (viewModel.navigateToWelcomeFragment) {
                        viewModel.navigateToWelcomeFragment = false
                        findNavController().navigate(R.id.action_mainFragment_to_welcomeFragment)
                    }
                }
            }
        }
    }

    fun login(){
        val client = AuthenticationAPIClient(account)
        val manager = CredentialsManager(client, SharedPreferencesStorage(requireContext()))
        if (manager.hasValidCredentials()) {
            val uId: Flow<Int> = dataStore.data
                .map { preferences ->
                    preferences[USER_ID] ?: -1
                }

            uId.onEach {
                if (it == -1) {
                    Auth.logout(account, requireContext(), dataStore)
                    viewModel.navigateToWelcomeFragment = true
                } else {
                    manager.getCredentials(object :
                        Callback<Credentials, CredentialsManagerException> {
                        override fun onSuccess(result: Credentials) {
                            Auth.withUserEmail(account, result.accessToken, onFailFunction = {
                                viewModel.error = "Was not able to connect you! Please try again later"
                            }) { email ->
                                viewModel.getProfileId(email)
                            }
                        }

                        override fun onFailure(error: CredentialsManagerException) {
                            Log.i("WelcomeFragment", "Error has occurred $error")
                        }
                    }
                    )
                }
            }.launchIn(lifecycleScope)
        } else {
            viewModel.navigateToWelcomeFragment = true
        }
    }
}