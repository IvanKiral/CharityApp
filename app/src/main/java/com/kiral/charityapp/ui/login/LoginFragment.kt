package com.kiral.charityapp.ui.login

/*@AndroidEntryPoint
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
}*/

