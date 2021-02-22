package com.kiral.charityapp.ui.main

import androidx.lifecycle.ViewModel
import com.kiral.charityapp.repositories.charities.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    val profileRepository: ProfileRepository
): ViewModel() {

    fun getProfileId(email: String): Int?{
        return profileRepository.login(email)?.id
    }
}