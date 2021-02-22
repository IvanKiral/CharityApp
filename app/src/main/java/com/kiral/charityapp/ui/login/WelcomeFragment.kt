package com.kiral.charityapp.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
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
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.theme.CharityTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : Fragment() {
    @Inject
    lateinit var dataStore: DataStore<Preferences>

    private val viewModel: WelcomeViewModel by viewModels()
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
        return ComposeView(requireContext()).apply {
            setContent {
                CharityTheme {
                    WelcomeScreen()
                }
            }
        }
    }

    @Composable
    fun WelcomeScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomStart),
                onClick = {
                    loginWithBrowser()
                }
            ) {
                Text("Login into application")
            }
        }
    }

    suspend fun write_id(id: Int) {
        dataStore.edit { settings ->
            settings[USER_ID] = id
        }
    }

    private fun loginWithBrowser() {
        val apiClient = AuthenticationAPIClient(account)
        val manager = CredentialsManager(apiClient, SharedPreferencesStorage(requireContext()))

        WebAuthProvider.login(account)
            .withScheme("demo")
            .withScope("openid profile email")
            .start(requireContext(), object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    // Something went wrong!
                }

                override fun onSuccess(result: Credentials) {
                    val accessToken = result.accessToken
                    manager.saveCredentials(result)
                    navigate(account, accessToken) { email ->
                        val id = viewModel.getProfileId(email)
                        id?.let { userId ->
                            /*lifecycleScope.launch {
                                write_id(userId)
                            }*/
                            findNavController().navigate(R.id.action_welcomeFragment_to_charitiesFragment)
                        }
                        if (id == null) {
                            val action =
                                WelcomeFragmentDirections.actionWelcomeFragmentToEditPersonalInformationFragment(
                                    email
                                )
                            findNavController().navigate(action)
                        }
                    }

                }
            })
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
}