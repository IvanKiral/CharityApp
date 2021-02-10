package com.kiral.charityapp.ui.profile

import androidx.lifecycle.ViewModel
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.repositories.charities.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val profileRepository: ProfileRepository
): ViewModel() {
    lateinit var profile: Profile

    fun setProfile(email: String){
        profile = profileRepository.getProfile(email)
    }
}