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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.ui.tooling.preview.Preview
import com.kiral.charityapp.R
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
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Login",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier//.align(Alignment.CenterHorizontally)
                        .padding(top = 96.dp, bottom = 64.dp)
                )
                LoginTextField(
                    text = loginText,
                    onChange = setLoginText,
                    label = "Email"
                )
                LoginTextField(
                    text = passwordText,
                    onChange = setPasswordText,
                    label = "Password",
                    password = true
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 8.dp)
                        .preferredHeight(64.dp),
                    onClick = { findNavController().navigate(R.id.action_loginFragment_to_editPersonalInformationFragment) }
                ) {
                    Text("Login", style = MaterialTheme.typography.button)
                }

                Text(
                    text = "Or login with",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(top = 32.dp, bottom = 16.dp)
                )

                IconRow()
                Text(
                    text = "Don't have an account?",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(top = 32.dp, bottom = 16.dp)
                )
                ClickableText(
                    text = AnnotatedString("Register"),
                    //modifier = Modifier.padding(top = 8.dp),
                    onClick = { /*TODO*/ }
                )

            }
        }
    }

    @Preview("LoginScreenPreview")
    @Composable
    fun LoginScreenPreview() {
        Scaffold {
            LoginScreen()
        }
    }
}



@Composable
fun LoginTextField(
    text: String,
    onChange: (String) -> Unit,
    label: String, password:
    Boolean = false
) {
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            //.preferredHeight(64.dp)
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp)
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
            /*label = {
                Text(
                    text = label,
                    style = androidx.compose.material.AmbientTextStyle.current.copy(
                        //fontFamily = Helvetica,
                        fontSize = 18.sp,
                        //fontWeight = FontWeight.Normal
                    )
                )
            },*/
            placeholder = {
                Text(
                    text = label,
                    style = labelTextStyle
                )
            },
            modifier = Modifier.padding(4.dp)
        )
    }
}


@Composable
fun ClickableLogo(
    icon: ImageVector,
    onIconClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        imageVector = icon,
        contentScale = ContentScale.Crop,
        modifier = modifier.clickable(onClick = onIconClicked)
    )
}

@Composable
fun IconRow() {
    val context = AmbientContext.current
    Row {
        ClickableLogo(icon = vectorResource(
            id = R.drawable.ic_google
        ),
            modifier = Modifier.padding(end = 8.dp),
            onIconClicked = {
                Toast.makeText(context, "Google!", Toast.LENGTH_SHORT).show()
            }
        )
        ClickableLogo(icon = vectorResource(
            id = R.drawable.ic_logos_facebook
        ),
            modifier = Modifier.padding(start = 8.dp),
            onIconClicked = {
                Toast.makeText(context, "Facebook!", Toast.LENGTH_SHORT).show()
            }
        )
    }
}


