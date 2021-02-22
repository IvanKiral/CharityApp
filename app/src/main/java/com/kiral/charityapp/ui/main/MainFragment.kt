package com.kiral.charityapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.kiral.charityapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {
    @Inject
    lateinit var dataStore: DataStore<Preferences>

    private val viewModel: MainViewModel by viewModels()

    private lateinit var account: Auth0

    val USER_ID = intPreferencesKey("user_id")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        account = Auth0(
            getString(R.string.com_auth0_client_id),
            getString(R.string.com_auth0_domain)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val client = AuthenticationAPIClient(account)
        val manager = CredentialsManager(client, SharedPreferencesStorage(requireContext()))
        if (manager.hasValidCredentials()) {
            /*val uId: Flow<Int> = dataStore.data
                .map { preferences ->
                    preferences[USER_ID] ?: -1
                }
            uId.asLiveData().observe(viewLifecycleOwner){
                if(it == -1){
                    logout()
                } else {
                    findNavController().navigate(R.id.action_welcomeFragment_to_charitiesFragment)
                }
            }*/
            manager.getCredentials(object : Callback<Credentials, CredentialsManagerException> {
                override fun onSuccess(result: Credentials) {
                    Log.i("WelcomeFragment", "credentials obtained!")
                    navigate(account, result.accessToken) { email ->
                        val id = viewModel.getProfileId(email)
                        if (id == null) {
                            logout()
                            findNavController().navigate(R.id.action_mainFragment_to_welcomeFragment)
                        } else {
                            findNavController().navigate(R.id.action_mainFragment_to_charitiesFragment)
                        }
                    }

                }

                override fun onFailure(error: CredentialsManagerException) {
                    Log.i("WelcomeFragment", "Error has occured ${error}")
                }
            }
            )
        }
        else{
            findNavController().navigate(R.id.action_mainFragment_to_welcomeFragment)
        }
        return ComposeView(requireContext()).apply {
            setContent {
            }
        }
    }

    private fun navigate(
        account: Auth0,
        accessToken: String,
        onSuccessFunction: (String) -> Unit,
    ) {
        val client = AuthenticationAPIClient(account)

        client.userInfo(accessToken)
            .start(object : Callback<UserProfile, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    Log.i("CharitiesFragment", "Somethings wrong")
                    // Something went wrong!
                }

                override fun onSuccess(result: UserProfile) {
                    onSuccessFunction(result.email!!)
                }
            })
    }

    private fun logout() {
        val client = AuthenticationAPIClient(account)
        val manager = CredentialsManager(client, SharedPreferencesStorage(requireContext()))
        WebAuthProvider.logout(account)
            .withScheme("demo")
            .start(requireContext(), object : Callback<Void?, AuthenticationException> {
                override fun onSuccess(result: Void?) {
                    manager.clearCredentials()
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStore.edit {
                            it.clear()
                        }
                    }
                }

                override fun onFailure(error: AuthenticationException) {
                    // Something went wrong!
                }
            })
    }
}