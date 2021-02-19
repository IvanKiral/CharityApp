package com.kiral.charityapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.components.ClickableIcon
import com.kiral.charityapp.ui.components.FormTextField
import com.kiral.charityapp.ui.components.InformationAlertDialog
import com.kiral.charityapp.ui.theme.CharityTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

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
                                val action = LoginFragmentDirections.actionLoginFragmentToCharitiesFragment(viewModel.loginText.value)
                                findNavController().navigate(action)
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
                    onClick = { findNavController().navigate(R.id.action_loginFragment_to_registrationFragment )}
                )
            }
        }
    }

    @Composable
    fun IconRow(
        modifier: Modifier = Modifier
    ) {
        val context = AmbientContext.current
        Row(modifier) {
            ClickableIcon(
                icon = vectorResource(id = R.drawable.ic_google),
                modifier = Modifier.padding(end = 8.dp),
                onIconClicked = {
                    Toast.makeText(context, "Google!", Toast.LENGTH_SHORT).show()
                }
            )
            ClickableIcon(
                icon = vectorResource(id = R.drawable.ic_logos_facebook),
                modifier = Modifier.padding(start = 8.dp),
                onIconClicked = {
                    Toast.makeText(context, "Facebook!", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

