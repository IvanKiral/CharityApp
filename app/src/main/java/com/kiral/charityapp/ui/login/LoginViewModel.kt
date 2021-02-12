package com.kiral.charityapp.ui.login

import androidx.lifecycle.ViewModel
import com.kiral.charityapp.domain.profiles
import com.kiral.charityapp.repositories.charities.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel
@Inject
constructor(
    val profileRepository: ProfileRepository
): ViewModel() {

    fun profileExists(email: String): Boolean{
        return profileRepository.login(email) != null
    }
}