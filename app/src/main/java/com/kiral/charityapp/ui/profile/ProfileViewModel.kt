package com.kiral.charityapp.ui.profile

import androidx.lifecycle.ViewModel
import com.kiral.charityapp.repositories.charities.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val profileRepository: ProfileRepository
): ViewModel() {
    val profile = profileRepository.getProfile()
}