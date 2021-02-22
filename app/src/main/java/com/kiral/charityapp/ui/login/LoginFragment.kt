package com.kiral.charityapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.ClickableText
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.components.ClickableIcon
import com.kiral.charityapp.ui.components.FormTextField
import com.kiral.charityapp.ui.theme.CharityTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var account: Auth0
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                LoginScreen()
            }
        }
    }

    @Composable
    fun LoginScreen() {
        val scrollState = rememberScrollState()
        CharityTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.LoginFragment_Title),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .padding(top = 96.dp, bottom = 64.dp)
                )

                FormTextField(
                    text = viewModel.loginText.value,
                    onChange = { viewModel.setLoginText(it) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    label = stringResource(R.string.LoginFragment_Email)
                )

                FormTextField(
                    text = viewModel.passwordText.value,
                    onChange = { viewModel.setPasswordText(it) },
                    label = stringResource(R.string.LoginFragment_Password),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.padding(top = 16.dp)
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .preferredHeight(64.dp),
                    onClick = {
                        if (viewModel.loginText.value != "" && viewModel.passwordText.value != "") {
                            if(viewModel.profileExists(viewModel.loginText.value)) {
                                //val action = LoginFragmentDirections.actionLoginFragmentToCharitiesFragment(viewModel.loginText.value)
                                //findNavController().navigate(action)
                            }else{
                                Toast.makeText(requireContext(),"There is no user with such credentials", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(requireContext(),"Please fill up login fields", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text(
                        stringResource(R.string.LoginFragment_ButtonLogin),
                        style = MaterialTheme.typography.button
                    )
                }

                Text(
                    text = stringResource(R.string.LoginFragment_OrLoginWith),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(top = 36.dp, bottom = 20.dp)
                )

                IconRow()

                Text(
                    text = stringResource(R.string.LoginFragment_DontHaveAnAccount),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(top = 32.dp, bottom = 16.dp)
                )

                ClickableText(
                    text = AnnotatedString(stringResource(R.string.LoginFragment_Register)),
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    onClick = { /*findNavController().navigate(R.id.action_loginFragment_to_registrationFragment )*/}
                )
            }
        }
    }

    @Composable
    fun IconRow(
        modifier: Modifier = Modifier
    ) {
        Row(modifier) {
            ClickableIcon(
                icon = vectorResource(id = R.drawable.ic_google),
                modifier = Modifier.padding(end = 8.dp),
                onIconClicked = {
                    loginWithBrowser()
                }
            )
            ClickableIcon(
                icon = vectorResource(id = R.drawable.ic_logos_facebook),
                modifier = Modifier.padding(start = 8.dp),
                onIconClicked = {
                    logout()
                }
            )
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
                }

                override fun onSuccess(result: Credentials) {
                    manager.saveCredentials(result)


                }
            })
    }
    private fun logout() {
        WebAuthProvider.logout(account)
            .withScheme("demo")
            .start(requireContext(), object: Callback<Void?, AuthenticationException> {
                override fun onSuccess(result: Void?) {
                    // The user has been logged out!
                }

                override fun onFailure(error: AuthenticationException) {
                    // Something went wrong!
                }
            })
    }
}

