package com.kiral.charityapp.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kiral.charityapp.repositories.charities.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel
@Inject
constructor(
    val profileRepository: ProfileRepository
): ViewModel() {

    val emailText = mutableStateOf("")
    val passwordText = mutableStateOf("")
    val checkPasswordText = mutableStateOf("")


    fun profileExists(email: String): Boolean {
        return profileRepository.login(email) != null
    }

    fun setEmailText(value: String){
        emailText.value = value
    }

    fun setPasswordText(value: String){
        passwordText.value = value
    }
    fun setCheckPasswordText(value: String){
        checkPasswordText.value = value
    }
}