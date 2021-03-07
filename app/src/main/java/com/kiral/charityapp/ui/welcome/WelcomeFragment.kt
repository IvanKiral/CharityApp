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
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.utils.Auth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    private val viewModel: WelcomeViewModel by viewModels()

    @Inject
    lateinit var account: Auth0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                viewModel.shouldNavigateToHomeFragment.value?.let {
                    if (it) {
                        findNavController().navigate(R.id.action_welcomeFragment_to_charitiesFragment)
                    } else {
                        val action =
                            WelcomeFragmentDirections.actionWelcomeFragmentToEditPersonalInformationFragment(
                                viewModel.email!!
                            )
                        findNavController().navigate(action)
                    }
                }
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
                text = "It's good to have you here! Start donating to charities by login into the app \u2665",
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


    private fun loginWithBrowser() {
        val apiClient = AuthenticationAPIClient(account)
        val manager = CredentialsManager(apiClient, SharedPreferencesStorage(requireContext()))

        WebAuthProvider.login(account)
            .withScheme("demo")
            .withScope("openid profile email")
            .start(requireContext(), object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {}

                override fun onSuccess(result: Credentials) {
                    val accessToken = result.accessToken
                    manager.saveCredentials(result)
                    Auth.withUserEmail(account, accessToken) { email ->
                        viewModel.getProfileId(email)
                    }
                }
            })
    }
}