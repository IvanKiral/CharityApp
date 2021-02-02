package com.kiral.charityapp.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.ui.tooling.preview.Preview
import com.kiral.charityapp.R
import com.kiral.charityapp.components.ClickableIcon
import com.kiral.charityapp.theme.CharityTheme
import com.kiral.charityapp.theme.TextFieldBorder
import com.kiral.charityapp.theme.labelTextStyle

class LoginFragment : Fragment() {

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
        CharityTheme {
            val (loginText, setLoginText) = remember { mutableStateOf("") }
            val (passwordText, setPasswordText) = remember { mutableStateOf("") }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.LoginFragment_Title),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .padding(top = 96.dp, bottom = 64.dp)
                )

                LoginTextField(
                    text = loginText,
                    onChange = setLoginText,
                    label = stringResource(R.string.LoginFragment_Email)
                )

                LoginTextField(
                    text = passwordText,
                    onChange = setPasswordText,
                    label = stringResource(R.string.LoginFragment_Password),
                    password = true,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .preferredHeight(64.dp),
                    onClick = { findNavController().navigate(R.id.action_loginFragment_to_editPersonalInformationFragment) }
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
                    onClick = { /*TODO*/ }
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

@Composable
fun LoginTextField(
    text: String,
    onChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    password: Boolean = false
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .border(
                width = 1.dp,
                color = TextFieldBorder,
                shape = RoundedCornerShape(6.dp)
            )
    ) {
        TextField(
            textStyle = labelTextStyle,
            backgroundColor = Color.Transparent,
            value = text,
            inactiveColor = Color.Transparent,
            onValueChange = onChange,
            visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None,
            placeholder = {
                Text(
                    text = label,
                    style = labelTextStyle
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}