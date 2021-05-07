package com.kiral.charityapp.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
import com.kiral.charityapp.ui.dataStore
import com.kiral.charityapp.ui.theme.CharityTheme
import com.kiral.charityapp.ui.theme.TextError
import com.kiral.charityapp.utils.Auth
import com.kiral.charityapp.utils.Auth.logout
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
                when {
                    viewModel.shouldNavigateToHomeFragment -> {
                        findNavController()
                            .navigate(R.id.action_welcomeFragment_to_charitiesFragment)
                    }
                    viewModel.shouldNavigateToEditPersonalInformationFragment -> {
                        val action =
                            WelcomeFragmentDirections.actionWelcomeFragmentToEditPersonalInformationFragment(
                                viewModel.email!!
                            )
                        findNavController().navigate(action)
                    }
                }
                CharityTheme{
                    WelcomeScreen(
                        account = account,
                        viewModel = viewModel,
                        login = { loginWithBrowser() }
                    )
                }
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

@Composable
fun WelcomeScreen(
    account: Auth0,
    viewModel: WelcomeViewModel,
    login: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .padding(24.dp)
                .offset(y = -25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cherrities",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_logo),
                contentDescription = stringResource(R.string.WelcomeScreen_LogoDescription),
                modifier = Modifier.padding()
            )
            if (viewModel.error != null) {
                logout(
                    account,
                    LocalContext.current,
                    LocalContext.current.dataStore
                )
                Text(
                    text = viewModel.error!!,
                    style = MaterialTheme.typography.body2.copy(color = TextError),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .height(56.dp),
            onClick = {
                login()
            }
        ) {
            Text(stringResource(R.string.welcome_login))
        }
    }
}