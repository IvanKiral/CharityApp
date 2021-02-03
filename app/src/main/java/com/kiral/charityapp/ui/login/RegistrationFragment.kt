package com.kiral.charityapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.components.ClickableIcon
import com.kiral.charityapp.ui.components.FormTextField
import com.kiral.charityapp.ui.theme.CharityTheme

class RegistrationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                RegistrationScreen()
            }
        }
    }

    @Composable
    fun RegistrationScreen() {
        CharityTheme {
            val (loginText, setLoginText) = remember { mutableStateOf("") }
            val (passwordText, setPasswordText) = remember { mutableStateOf("") }
            val (checkPasswordText, setCheckPasswordText) = remember { mutableStateOf("") }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.RegistrationFragment_Title),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .padding(top = 96.dp, bottom = 32.dp)
                )

                FormTextField(
                    text = loginText,
                    onChange = setLoginText,
                    label = stringResource(R.string.RegistrationFragment_Email)
                )

                FormTextField(
                    text = passwordText,
                    onChange = setPasswordText,
                    label = stringResource(R.string.RegistrationFragment_Password),
                    modifier = Modifier.padding(top = 16.dp)
                )

                FormTextField(
                    text = checkPasswordText,
                    onChange = setCheckPasswordText,
                    label = stringResource(R.string.RegistrationFragment_PasswordConfirmation),
                    password = true,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .preferredHeight(64.dp),
                    onClick = { }
                ) {
                    Text(
                        stringResource(R.string.RegistrationFragment_CreateAccount),
                        style = MaterialTheme.typography.button
                    )
                }

                Text(
                    text = stringResource(R.string.RegistrationFragment_SignUpWith),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(top = 36.dp, bottom = 20.dp)
                )

                IconRow()

                Text(
                    text = stringResource(R.string.RegistrationFragment_AlreadyRegistered),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(top = 32.dp, bottom = 16.dp)
                )

                ClickableText(
                    text = AnnotatedString(stringResource(R.string.RegistrationFragment_Login)),
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    onClick = {
                        findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
                    }
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

