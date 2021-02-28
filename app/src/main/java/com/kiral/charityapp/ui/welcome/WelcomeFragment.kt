package com.kiral.charityapp.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.utils.Auth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : Fragment() {
    @Inject
    lateinit var dataStore: DataStore<Preferences>

    private val viewModel: WelcomeViewModel by viewModels()
    @Inject
    lateinit var account: Auth0

    val USER_ID = intPreferencesKey("user_id")

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cherrities",
                style = MaterialTheme.typography.h4
            )
            Text(
                text="It's good to have you here! Start donating to charities by login into the app \u2665",
                style = MaterialTheme.typography.body2.copy(color = Color.Black.copy(alpha = 0.75f)),
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(top = 32.dp)
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .height(60.dp),
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
                    Auth.withUserEmail(account, accessToken) { email ->
                        val id = viewModel.getProfileId(email)
                        id?.let { userId ->
                            lifecycleScope.launch {
                                write_id(userId)
                            }
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
}