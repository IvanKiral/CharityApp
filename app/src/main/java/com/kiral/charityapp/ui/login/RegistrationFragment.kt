package com.kiral.charityapp.ui.login

/*import android.os.Bundle
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
import androidx.compose.ui.platform.AmbientContext
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
import com.kiral.charityapp.R
import com.kiral.charityapp.ui.components.ClickableIcon
import com.kiral.charityapp.ui.components.FormTextField
import com.kiral.charityapp.ui.theme.CharityTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private val viewModel: RegistrationViewModel by viewModels()

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
                    stringResource(R.string.RegistrationFragment_Title),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .padding(top = 96.dp, bottom = 32.dp)
                )

                FormTextField(
                    text = viewModel.emailText.value,
                    onChange = { viewModel.setEmailText(it) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    label = stringResource(R.string.RegistrationFragment_Email)
                )

                FormTextField(
                    text = viewModel.passwordText.value,
                    onChange = { viewModel.setPasswordText(it) },
                    label = stringResource(R.string.RegistrationFragment_Password),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.padding(top = 16.dp)
                )

                FormTextField(
                    text = viewModel.checkPasswordText.value,
                    onChange = { viewModel.setCheckPasswordText(it) },
                    label = stringResource(R.string.RegistrationFragment_PasswordConfirmation),
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
                        /*if(viewModel.emailText.value != "") {
                            val action = RegistrationFragmentDirections
                                .actionRegistrationFragmentToEditPersonalInformationFragment(viewModel.emailText.value)
                            findNavController().navigate(action)
                        } else{
                            Toast.makeText(requireContext(), "Please fill up your credentials", Toast.LENGTH_SHORT).show()
                        }*/
                    }
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
                        //findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
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
}*/

