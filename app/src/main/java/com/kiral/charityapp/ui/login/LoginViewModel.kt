package com.kiral.charityapp.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kiral.charityapp.repositories.charities.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel
@Inject
constructor(
    val profileRepository: ProfileRepository
): ViewModel() {

    val loginText = mutableStateOf("")

    val passwordText = mutableStateOf("")


    fun profileExists(email: String): Boolean {
        return profileRepository.login(email) != null
    }

    fun setLoginText(value: String){
        loginText.value = value
    }

    fun setPasswordText(value: String){
        passwordText.value = value
    }
}